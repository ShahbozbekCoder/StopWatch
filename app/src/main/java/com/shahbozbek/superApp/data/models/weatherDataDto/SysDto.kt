package com.shahbozbek.superApp.data.models.weatherDataDto

import com.shahbozbek.superApp.domain.model.weatherData.Sys

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