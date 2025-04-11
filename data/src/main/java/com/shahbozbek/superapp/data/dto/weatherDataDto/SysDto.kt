package com.shahbozbek.superapp.data.dto.weatherDataDto

import com.shahbozbek.superapp.domain.model.weatherData.Sys

data class SysDto(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

fun SysDto.toDomain(): Sys {
    return Sys(
        country = country,
        id = id,
        sunrise = sunrise,
        sunset = sunset,
        type = type
    )
}