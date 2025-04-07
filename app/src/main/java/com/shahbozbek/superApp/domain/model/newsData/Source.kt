package com.shahbozbek.superApp.domain.model.newsData

import android.os.Parcelable
import com.shahbozbek.superApp.data.models.newsDataDto.SourceDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String? = null,
    val name: String? = null
): Parcelable

fun Source.toDto(): SourceDto {
    return SourceDto(
        id = id,
        name = name
    )
}