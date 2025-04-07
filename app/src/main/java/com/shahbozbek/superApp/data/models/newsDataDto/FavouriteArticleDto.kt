package com.shahbozbek.superApp.data.models.newsDataDto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shahbozbek.superApp.domain.model.newsData.FavouriteArticle
import com.shahbozbek.superApp.presentation.utils.Constants.FAVOURITES_TABLE_NAME

@Entity(
    tableName = FAVOURITES_TABLE_NAME,
    indices = [Index(value = ["url"], unique = true)]
)
data class FavouriteArticleDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: SourceDto? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)



fun FavouriteArticleDto.fromArticle(articleDto: ArticleDto): FavouriteArticleDto {
    return FavouriteArticleDto(
        id = articleDto.id,
        author = articleDto.author,
        content = articleDto.content,
        description = articleDto.description,
        publishedAt = articleDto.publishedAt,
        source = articleDto.sourceDto,
        title = articleDto.title,
        url = articleDto.url,
        urlToImage = articleDto.urlToImage
    )
}

fun FavouriteArticleDto.toDomain(): FavouriteArticle {
    return FavouriteArticle(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.toDomain(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}
