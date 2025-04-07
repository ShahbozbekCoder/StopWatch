package com.shahbozbek.superApp.domain.model.weatherData

import com.shahbozbek.superApp.data.models.weatherDataDto.WindDto

data class Wind(
    val deg: Int,
    val speed: Double
)
fun Wind.toDto(): WindDto {
    return WindDto(
        deg = deg,
        speed = speed
    )
}