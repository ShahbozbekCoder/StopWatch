package com.shahbozbek.superApp.domain.model.weatherData

import com.shahbozbek.superApp.data.models.weatherDataDto.WeatherDataDto

data class WeatherData(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
fun WeatherData.toDto(): WeatherDataDto {
    return WeatherDataDto(
        base = base,
        cloudsDto = clouds.toDto(),
        cod = cod,
        coordDto = coord.toDto(),

        dt = dt,

        id = id,

        mainDto = main.toDto(),
        name = name,
        sysDto = sys.toDto(),
        timezone = timezone,
        visibility = visibility,
        weatherDto = weather.map { it.toDto() },
        windDto = wind.toDto(),
    )

}