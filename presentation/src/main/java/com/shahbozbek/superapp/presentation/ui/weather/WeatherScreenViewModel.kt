package com.shahbozbek.superapp.presentation.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.superapp.domain.datastate.WeatherUIState
import com.shahbozbek.superapp.domain.usecases.GetWeatherDataUseCase
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