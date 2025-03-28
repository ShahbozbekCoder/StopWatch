package com.shahbozbek.stopwatch.data.models.newsdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String? = null,
    val name: String? = null
): Parcelable