package com.robbyyehezkiel.androidfundamental2.utils

import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    fun applyTheme(themeMode: String) {
        val nightMode = when (themeMode) {
            "light" -> AppCompatDelegate.MODE_NIGHT_NO
            "dark" -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
