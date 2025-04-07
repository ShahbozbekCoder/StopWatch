package com.shahbozbek.superApp.data.models.newsdata

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shahbozbek.superApp.utils.Constants.ARTICLES_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = ARTICLES_TABLE_NAME,
    indices = [Index(value = ["url"], unique = true)]
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
): Parcelable