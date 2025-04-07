package com.shahbozbek.superApp.data.models.weatherDataDto

import com.shahbozbek.superApp.domain.model.weatherData.Weather

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