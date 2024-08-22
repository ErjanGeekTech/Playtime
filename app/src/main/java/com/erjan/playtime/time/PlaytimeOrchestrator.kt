package com.erjan.playtime.time

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.erjan.playtime.local.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlaytimeOrchestrator(
    private val preferencesHelper: PreferencesHelper,
    private val activity: AppCompatActivity?
) {

    private var currentState: Playtime = Playtime(preferencesHelper.playtime)

    private val _tickerState = MutableStateFlow(preferencesHelper.playtime.parseToTimeString())
    val tickerState: StateFlow<String> = _tickerState.asStateFlow()

    init {
        startJob()
    }

    private fun startJob() {
        activity?.lifecycleScope?.launch(Dispatchers.Default) {
            activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
    }

    companion object {
        const val PLAYTIME_DELAY_SECOND: Long = 1000
    }
}