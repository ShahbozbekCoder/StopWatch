package com.shahbozbek.stopwatch.ui.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.stopwatch.data.models.WeatherData
import com.shahbozbek.stopwatch.usecases.GetWeatherDataUseCase
import com.shahbozbek.stopwatch.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel() {

    private val _weatherData = MutableStateFlow<Result>(Result.Loading)
    val weatherData: MutableStateFlow<Result> get() = _weatherData

    fun getWeather() = viewModelScope.launch {
        getWeatherDataUseCase().collect { weather ->
            _weatherData.value = Result.Success(weather)
            Log.d("WeatherScreenViewModel", "WeatherData: $weather")
        }
    }
}