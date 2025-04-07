package com.shahbozbek.superApp.utils

import com.shahbozbek.superApp.data.models.newsdata.NewsData

sealed class Result {
    data object Loading : Result()
    data class Success(val data: NewsData) : Result()
    data class Error(val message: String) : Result()
}