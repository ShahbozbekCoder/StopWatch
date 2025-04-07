package com.shahbozbek.superApp.domain.model.newsData

data class NewsData(
    val articleDtos: List<Article>,
    val status: String? = null,
    val totalResults: Int? = null
)