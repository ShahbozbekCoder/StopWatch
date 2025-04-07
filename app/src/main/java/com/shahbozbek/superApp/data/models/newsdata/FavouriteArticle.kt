package com.shahbozbek.superApp.data.models.newsdata

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shahbozbek.superApp.utils.Constants.FAVOURITES_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = FAVOURITES_TABLE_NAME,
    indices = [Index(value = ["url"], unique = true)]
)
data class FavouriteArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
) : Parcelable {

    companion object {

        fun fromArticle(article: Article): FavouriteArticle {
            return FavouriteArticle(
                id = article.id,
                author = article.author,
                content = article.content,
                description = article.description,
                publishedAt = article.publishedAt,
                source = article.source,
                title = article.title,
                url = article.url,
                urlToImage = article.urlToImage
            )
        }
    }
}