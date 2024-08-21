package com.erjan.playtime.time

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.erjan.playtime.local.PreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlaytimeListOrchestrator(
    private val preferencesHelper: PreferencesHelper,
    private val scope: CoroutineScope,
) : DefaultLifecycleObserver {

    private var job: Job? = null
    private var currentState: Playtime = Playtime(preferencesHelper.playtime)

    private val _tickerState = MutableStateFlow(preferencesHelper.playtime.parseToTimeString())
    val tickerState: StateFlow<String> = _tickerState.asStateFlow()

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopJob()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (job == null) startJob()
    }

    private fun startJob() {
        job = scope.launch(Dispatchers.Default) {
            while (isActive) {
                val playtime = currentState.playtime + PLAYTIME_DELAY_SECOND
                val playtimeStr = playtime.parseToTimeString()
                currentState = Playtime(playtime = playtime, playtimeStr = playtimeStr)
                _tickerState.value = playtimeStr
                preferencesHelper.playtime = playtime

                delay(PLAYTIME_DELAY_SECOND)
            }
        }
    }

    private fun stopJob() {
        job?.cancel()
        job = null
    }

    companion object {
        const val PLAYTIME_DELAY_SECOND: Long = 1000
    }
}