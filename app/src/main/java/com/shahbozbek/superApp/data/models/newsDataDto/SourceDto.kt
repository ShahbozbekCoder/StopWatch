package com.shahbozbek.superApp.data.models.newsDataDto

import com.shahbozbek.superApp.domain.model.newsData.Source


data class SourceDto(
    val id: String? = null,
    val name: String? = null
)

fun SourceDto.toDomain(): Source {
    return Source(
        id = id,
        name = name
    )
}