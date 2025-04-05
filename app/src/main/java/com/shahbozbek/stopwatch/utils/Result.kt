package com.shahbozbek.stopwatch.utils

import com.shahbozbek.stopwatch.data.models.newsdata.NewsData

sealed class Result {
    data object Loading : Result()
    data class Success(val data: NewsData) : Result()
    data class Error(val message: String) : Result()
}