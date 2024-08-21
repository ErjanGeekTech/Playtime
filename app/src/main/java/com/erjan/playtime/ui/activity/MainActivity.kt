package com.erjan.playtime.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.erjan.playtime.R
import com.erjan.playtime.local.PreferencesHelper
import com.erjan.playtime.time.PlaytimeListOrchestrator

class MainActivity : AppCompatActivity(R.layout.activity_main), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orchestrator = PlaytimeListOrchestrator(
            preferencesHelper = PreferencesHelper(this),
            scope = lifecycleScope
        )

        lifecycle.addObserver(orchestrator)
    }
}