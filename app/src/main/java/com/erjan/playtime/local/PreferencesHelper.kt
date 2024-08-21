package com.erjan.playtime.local

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("playtime.preferences", Context.MODE_PRIVATE)

    var playtime: Long
        get() = preferences.getLong(PREF_PLAYTIME, 0)
        set(value) = preferences.edit().putLong(PREF_PLAYTIME, value).apply()

    companion object {
        const val PREF_PLAYTIME = "playtime"
    }
}