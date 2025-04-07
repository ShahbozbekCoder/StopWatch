package com.shahbozbek.superApp.usecases

import com.shahbozbek.superApp.data.models.weatherdata.WeatherData
import com.shahbozbek.superApp.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<WeatherData?> {
        return repository.getWeather()
    }
}