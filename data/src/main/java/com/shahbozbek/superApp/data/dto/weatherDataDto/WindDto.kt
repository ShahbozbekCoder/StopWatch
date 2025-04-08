package com.shahbozbek.superApp.data.dto.weatherDataDto

import com.shahbozbek.superapp.domain.model.weatherData.Wind

data class WindDto(
    val deg: Int,
    val speed: Double
)

fun WindDto.toDomain(): Wind {
    return Wind(
        deg = deg,
        speed = speed
    )
}