package com.shahbozbek.stopwatch.utils

import com.shahbozbek.stopwatch.data.models.newsdata.NewsData
import com.shahbozbek.stopwatch.data.models.weatherdata.WeatherData

sealed class Result {
    data object Loading : Result()
    data class Success(val data: NewsData) : Result()
    data class Error(val message: String) : Result()
}