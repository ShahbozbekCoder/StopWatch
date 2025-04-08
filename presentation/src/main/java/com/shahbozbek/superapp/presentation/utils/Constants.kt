package com.shahbozbek.superapp.presentation.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Constants {
    const val CITY_NAME = "Tashkent"
    val clouds = listOf(
        "Clouds", "Haze", "Foggy", "Partly Clouds", "Overcast", "Mist"
    )
    val rain = listOf(
        "Rain", "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain"
    )
    val snow = listOf(
        "Snow", "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard"
    )
    val sunny = listOf(
        "Sunny", "Clear", "Clear sky"
    )
    val categories = listOf(
        "General", "Business", "Entertainment", "Health", "Science", "Sports", "Technology"
    )
    val backgrounds = listOf(
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF7BCAEE),
                Color(0xFF2F4042)
            )
        ),
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF3D6070),
                Color(0xFF576D70)
            )
        ),
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFC2E1EF),
                Color(0xFF8BBDC0)
            )
        ),
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF46B5E7),
                Color(0xFF1BD9F1)
            )
        ),
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF8FCEEE),
                Color(0xFFC6D2D3)
            )
        )
    )
    const val STOP = "Stop"
    const val START = "Start"
    const val RESET = "Reset"
    const val HUMIDITY = "Humidity:"
    const val WIND_SPEED = "Wind Speed:"
    const val PRESSURE = "Pressure:"
    const val SUNRISE = "Sunrise:"
    const val SUNSET = "Sunset:"
    const val LAST_UPDATE = "Last update: "

}