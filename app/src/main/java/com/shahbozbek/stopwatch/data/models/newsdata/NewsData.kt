package com.shahbozbek.stopwatch.data.models.newsdata

data class NewsData(
    val articles: List<Article>,
    val status: String? = null,
    val totalResults: Int? = null
)