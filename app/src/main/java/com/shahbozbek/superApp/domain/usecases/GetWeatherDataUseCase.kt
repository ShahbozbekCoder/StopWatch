package com.shahbozbek.superApp.domain.usecases

import com.shahbozbek.superApp.domain.model.weatherData.WeatherData
import com.shahbozbek.superApp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<WeatherData?> {
        return repository.getWeather()
    }
}