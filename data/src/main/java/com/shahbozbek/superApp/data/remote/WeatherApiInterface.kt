package com.shahbozbek.superApp.data.remote

import com.shahbozbek.superApp.data.dto.weatherDataDto.WeatherDataDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_FROM_WEATHER = "999f6e9a047b7a91fb0bbc79df3ebfe6"

interface WeatherApiInterface {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String = "Tashkent",
        @Query("appid") apiKey: String = API_FROM_WEATHER
    ): Response<WeatherDataDto>

}