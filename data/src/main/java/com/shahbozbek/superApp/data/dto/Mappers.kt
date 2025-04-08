package com.shahbozbek.superApp.data.dto

import com.shahbozbek.superApp.data.dto.newsDataDto.ArticleDto
import com.shahbozbek.superApp.data.dto.newsDataDto.FavouriteArticleDto
import com.shahbozbek.superApp.data.dto.newsDataDto.SourceDto
import com.shahbozbek.superapp.domain.model.newsData.Article
import com.shahbozbek.superapp.domain.model.newsData.FavouriteArticle
import com.shahbozbek.superapp.domain.model.newsData.Source

fun Article.toDto(): ArticleDto {
    return ArticleDto(
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

fun Article.toFavouriteArticleDto(): FavouriteArticleDto {
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

fun FavouriteArticle.toDto(): FavouriteArticleDto {
    return FavouriteArticleDto(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.toDto(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun Source.toDto(): SourceDto {
    return SourceDto(
        id = id,
        name = name
    )
}