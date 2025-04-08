package com.shahbozbek.superapp.domain.usecases

import com.shahbozbek.superapp.domain.model.weatherData.WeatherData
import com.shahbozbek.superapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<WeatherData?> {
        return repository.getWeather()
    }
}