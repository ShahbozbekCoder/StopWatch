package com.shahbozbek.superapp.data.dto.newsDataDto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shahbozbek.superapp.data.utils.Constants.ARTICLES_TABLE_NAME
import com.shahbozbek.superapp.domain.model.newsData.Article

@Entity(
    tableName = ARTICLES_TABLE_NAME,
    indices = [Index(value = ["url"], unique = true)]
)
data class ArticleDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: SourceDto? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
)

fun ArticleDto.toDomain(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.toDomain(),
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}