package com.shahbozbek.superapp.data.dto.weatherDataDto

import com.shahbozbek.superapp.domain.model.weatherData.Weather

data class WeatherDto(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

fun WeatherDto.toDomain(): Weather {
    return Weather(
        description = description,
        icon = icon,
        id = id,
        main = main
    )
}