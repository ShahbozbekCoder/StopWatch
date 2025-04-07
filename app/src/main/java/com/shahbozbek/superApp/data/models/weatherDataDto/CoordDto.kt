package com.shahbozbek.superApp.data.models.weatherDataDto

import com.shahbozbek.superApp.domain.model.weatherData.Coord

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