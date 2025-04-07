package com.shahbozbek.superApp.presentation.ui.theme

import android.content.Context

class ThemePreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    companion object {
        const val THEME_KEY = "theme_mode"
    }

    fun saveTheme(isDarkThemeEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(THEME_KEY, isDarkThemeEnabled).apply()

    }

    fun isDarkThemeEnabled(): Boolean {
        return sharedPreferences.getBoolean(THEME_KEY, false)
    }
}