package com.shahbozbek.superApp.data.models.weatherDataDto

import com.shahbozbek.superApp.domain.model.weatherData.Clouds

data class CloudsDto(
    val all: Int
)
fun CloudsDto.toDomain(): Clouds {
    return Clouds(
        all = all
    )
}