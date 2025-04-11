package com.shahbozbek.superapp.data.dto.weatherDataDto

import com.shahbozbek.superapp.domain.model.weatherData.Main

data class MainDto(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

fun MainDto.toDomain(): Main {
    return Main(
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