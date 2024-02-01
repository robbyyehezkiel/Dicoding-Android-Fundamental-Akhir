package com.robbyyehezkiel.androidfundamental2.utils

import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    fun applyTheme(themeMode: String) {
        when (themeMode) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
