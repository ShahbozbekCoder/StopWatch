package com.shahbozbek.superApp.domain.model.weatherData

import com.shahbozbek.superApp.data.models.weatherDataDto.CloudsDto

data class Clouds(
    val all: Int
)
fun Clouds.toDto(): CloudsDto {
    return CloudsDto(
        all = all
    )
}