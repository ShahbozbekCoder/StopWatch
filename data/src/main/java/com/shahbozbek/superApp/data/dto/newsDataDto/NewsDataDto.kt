package com.shahbozbek.superApp.data.dto.newsDataDto

import com.shahbozbek.superapp.domain.model.newsData.NewsData

data class NewsDataDto(
    val articles: List<ArticleDto>,
    val status: String? = null,
    val totalResults: Int? = null
)

fun NewsDataDto.toDomain(): NewsData {
    return NewsData(
        articles = articles.map { it.toDomain() },
        status = status.orEmpty(),
        totalResults = totalResults ?: 0
    )
}