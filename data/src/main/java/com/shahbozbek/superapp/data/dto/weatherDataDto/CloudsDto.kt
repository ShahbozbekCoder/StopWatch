package com.shahbozbek.superapp.data.dto.weatherDataDto

import com.shahbozbek.superapp.domain.model.weatherData.Clouds

data class CloudsDto(
    val all: Int
)

fun CloudsDto.toDomain(): Clouds {
    return Clouds(
        all = all
    )
}