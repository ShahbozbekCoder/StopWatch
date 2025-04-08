package com.shahbozbek.superApp.data.dto.weatherDataDto

import com.shahbozbek.superapp.domain.model.weatherData.Coord

data class CoordDto(
    val lat: Double,
    val lon: Double
)

fun CoordDto.toDomain(): Coord {
    return Coord(
        lat = lat,
        lon = lon
    )
}