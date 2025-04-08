package com.shahbozbek.superapp.domain.datastate

import com.shahbozbek.superapp.domain.model.weatherData.WeatherData

data class WeatherUIState(
    val isLoading: Boolean,
    val weatherData: WeatherData?,
    val error: String?
)
