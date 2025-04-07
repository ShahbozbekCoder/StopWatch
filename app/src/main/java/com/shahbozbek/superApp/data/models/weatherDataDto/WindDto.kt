package com.shahbozbek.superApp.data.models.weatherDataDto

import com.shahbozbek.superApp.domain.model.weatherData.Wind

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