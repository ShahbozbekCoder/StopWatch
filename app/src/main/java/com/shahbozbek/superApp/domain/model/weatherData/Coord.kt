package com.shahbozbek.superApp.domain.model.weatherData

import com.shahbozbek.superApp.data.models.weatherDataDto.CoordDto

data class Coord(
    val lat: Double,
    val lon: Double
)
fun Coord.toDto(): CoordDto {
    return CoordDto(
        lat = lat,
        lon = lon
    )
}