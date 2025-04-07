package com.shahbozbek.superApp.data.models.weatherDataDto

import com.shahbozbek.superApp.domain.model.weatherData.WeatherData

data class WeatherDataDto(
    val base: String,
    val cloudsDto: CloudsDto,
    val cod: Int,
    val coordDto: CoordDto,
    val dt: Int,
    val id: Int,
    val mainDto: MainDto,
    val name: String,
    val sysDto: SysDto,
    val timezone: Int,
    val visibility: Int,
    val weatherDto: List<WeatherDto>,
    val windDto: WindDto
)

fun WeatherDataDto.toDomain(): WeatherData {
    return WeatherData(
        base = base,
        clouds = cloudsDto.toDomain(),
        cod = cod,
        coord = coordDto.toDomain(),
        dt = dt,
        id = id,
        main = mainDto.toDomain(),
        name = name,
        sys = sysDto.toDomain(),
        timezone = timezone,
        visibility = visibility,
        weather = weatherDto.map { it.toDomain() },
        wind = windDto.toDomain(),
    )
}