package com.shahbozbek.superApp.domain.datastate

import com.shahbozbek.superApp.domain.model.weatherData.WeatherData

data class WeatherUIState(
    val isLoading: Boolean,
    val weatherData: WeatherData?,
    val error: String?
)
