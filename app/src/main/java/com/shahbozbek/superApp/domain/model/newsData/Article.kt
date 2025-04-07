package com.shahbozbek.superApp.domain.model.newsData

import android.os.Parcelable
import com.shahbozbek.superApp.data.models.newsDataDto.ArticleDto
import com.shahbozbek.superApp.data.models.newsDataDto.FavouriteArticleDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
) : Parcelable

fun Article.toDto(): ArticleDto {
    return ArticleDto(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceDto = source?.toDto(),
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}

fun Article.toEntity(): FavouriteArticleDto {
    return FavouriteArticleDto(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.toDto(),
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}