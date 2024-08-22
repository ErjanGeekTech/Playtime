package com.erjan.playtime.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erjan.playtime.R
import com.erjan.playtime.local.PreferencesHelper
import com.erjan.playtime.time.PlaytimeListOrchestrator

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlaytimeListOrchestrator(
            preferencesHelper = PreferencesHelper(this), this
        )
    }
}