package com.shahbozbek.superApp.domain.model.weatherData

import com.shahbozbek.superApp.data.models.weatherDataDto.SysDto

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)
fun Sys.toDto(): SysDto {
    return SysDto(
        country = country,
        id = id,
        sunrise = sunrise,
        sunset = sunset,
        type = type
    )
}