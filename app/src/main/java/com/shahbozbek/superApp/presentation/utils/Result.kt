package com.shahbozbek.superApp.presentation.utils

import com.shahbozbek.superApp.domain.model.newsData.NewsData

sealed class Result {
    data object Loading : Result()
    data class Success(val data: NewsData) : Result()
    data class Error(val message: String) : Result()
}