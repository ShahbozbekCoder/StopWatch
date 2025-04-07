package com.shahbozbek.superApp.data.models.newsDataDto

import com.shahbozbek.superApp.domain.model.newsData.NewsData

data class NewsDataDto(
    val articleDtos: List<ArticleDto>,
    val status: String? = null,
    val totalResults: Int? = null
)

fun NewsDataDto.toDomain(): NewsData {
    return NewsData(
        articleDtos = articleDtos.map { it.toDomain() },
        status = status,
        totalResults = totalResults
    )
}