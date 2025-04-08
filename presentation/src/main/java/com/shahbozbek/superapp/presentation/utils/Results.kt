package com.shahbozbek.superapp.presentation.utils

import com.shahbozbek.superapp.domain.model.newsData.NewsData

sealed class Results {
    data object Loading : Results()
    data class Success(val data: NewsData) : Results()
    data class Error(val message: String) : Results()
}