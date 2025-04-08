package com.shahbozbek.superapp.domain.model.newsData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavouriteArticle(
    val id: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
) : Parcelable
