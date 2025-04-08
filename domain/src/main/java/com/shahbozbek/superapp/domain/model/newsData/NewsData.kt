package com.shahbozbek.superapp.domain.model.newsData

data class NewsData(
    val articles: List<Article>,
    val status: String? = null,
    val totalResults: Int? = null
)