package com.shahbozbek.superApp.domain.model.weatherData

import com.shahbozbek.superApp.data.models.weatherDataDto.MainDto

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)
fun Main.toDto(): MainDto {
    return MainDto(
        feels_like = feels_like,
        grnd_level = grnd_level,
        humidity = humidity,
        pressure = pressure,
        sea_level = sea_level,
        temp = temp,
        temp_max = temp_max,
        temp_min = temp_min
    )
}