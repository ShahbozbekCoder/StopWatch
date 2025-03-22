package com.shahbozbek.stopwatch.utils

import com.shahbozbek.stopwatch.data.models.weatherdata.WeatherData

sealed class Result {
    data object Loading : Result()
    data class Success(val data: WeatherData?) : Result()
    data class Error(val message: String) : Result()
}