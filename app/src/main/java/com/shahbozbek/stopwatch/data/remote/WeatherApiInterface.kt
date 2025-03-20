package com.shahbozbek.stopwatch.data.remote

import com.shahbozbek.stopwatch.data.models.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val API = "999f6e9a047b7a91fb0bbc79df3ebfe6"

interface WeatherApiInterface {

    @GET("weather")
    suspend fun getWeather(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Accept") accept: String = "application/json",
        @Query("q") city: String = "Tashkent",
        @Query("appid") apiKey: String = API
    ): Response<WeatherData>

}