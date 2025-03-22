package com.shahbozbek.stopwatch.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.stopwatch.data.models.datastate.WeatherUIState
import com.shahbozbek.stopwatch.usecases.GetWeatherDataUseCase
import com.shahbozbek.stopwatch.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        WeatherUIState(
            isLoading = false,
            weatherData = null,
            error = null
        )
    )
    val state: StateFlow<WeatherUIState> get() = _state.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = _state.value.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    fun getWeather() = viewModelScope.launch(coroutineExceptionHandler) {

        _state.value = _state.value.copy(
            isLoading = true
        )

        getWeatherDataUseCase().collect { weather ->
            _state.value = _state.value.copy(
                isLoading = false,
                error = null,
                weatherData = weather
            )
        }
    }
}