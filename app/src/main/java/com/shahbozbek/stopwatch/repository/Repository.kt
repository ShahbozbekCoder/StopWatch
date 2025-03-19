package com.shahbozbek.stopwatch.repository

import com.shahbozbek.stopwatch.data.models.WeatherData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getTime(): Long

    suspend fun saveTime(time: Long)

    fun getWeather(): Flow<WeatherData?>

}