package com.shahbozbek.superApp.domain.model.weatherData

import com.shahbozbek.superApp.data.models.weatherDataDto.WeatherDto

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
fun Weather.toDto(): WeatherDto {
    return WeatherDto(
        description = description,
        icon = icon,
        id = id,
        main = main
    )
}