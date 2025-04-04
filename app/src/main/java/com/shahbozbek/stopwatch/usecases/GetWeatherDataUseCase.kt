package com.shahbozbek.stopwatch.usecases

import com.shahbozbek.stopwatch.data.models.weatherdata.WeatherData
import com.shahbozbek.stopwatch.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<WeatherData?> {
        return repository.getWeather()
    }
}