package com.shahbozbek.stopwatch.data.models.datastate

import com.shahbozbek.stopwatch.data.models.weatherdata.WeatherData

data class WeatherUIState(
    val isLoading: Boolean,
    val weatherData: WeatherData?,
    val error: String?
)
