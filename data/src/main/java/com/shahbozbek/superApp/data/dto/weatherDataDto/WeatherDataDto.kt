package com.shahbozbek.superApp.data.dto.weatherDataDto

import com.shahbozbek.superapp.domain.model.weatherData.WeatherData

data class WeatherDataDto(
    val base: String,
    val clouds: CloudsDto,
    val cod: Int,
    val coord: CoordDto,
    val dt: Int,
    val id: Int,
    val main: MainDto,
    val name: String,
    val sys: SysDto,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherDto>,
    val wind: WindDto
)

fun WeatherDataDto.toDomain(): WeatherData {
    return WeatherData(
        base = base,
        clouds = clouds.toDomain(),
        cod = cod,
        coord = coord.toDomain(),
        dt = dt,
        id = id,
        main = main.toDomain(),
        name = name,
        sys = sys.toDomain(),
        timezone = timezone,
        visibility = visibility,
        weather = weather.map { it.toDomain() },
        wind = wind.toDomain(),
    )
}