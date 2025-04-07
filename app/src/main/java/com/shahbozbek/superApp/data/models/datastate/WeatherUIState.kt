package com.shahbozbek.superApp.data.models.datastate

import com.shahbozbek.superApp.data.models.weatherdata.WeatherData

data class WeatherUIState(
    val isLoading: Boolean,
    val weatherData: WeatherData?,
    val error: String?
)
