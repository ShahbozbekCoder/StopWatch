package com.shahbozbek.superApp.data.dto.newsDataDto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shahbozbek.superApp.data.utils.Constants.FAVOURITES_TABLE_NAME
import com.shahbozbek.superapp.domain.model.newsData.FavouriteArticle

@Entity(
    tableName = FAVOURITES_TABLE_NAME, indices = [Index(value = ["url"], unique = true)]
)
data class FavouriteArticleDto(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: SourceDto? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)

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
