package com.shahbozbek.superapp.presentation.ui.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeatherScreen(
    weatherScreenViewModel: WeatherScreenViewModel = hiltViewModel<WeatherScreenViewModel>(),
    paddingValues: PaddingValues
) {
    val currentTime = remember {
        mutableLongStateOf(System.currentTimeMillis())
    }

    LaunchedEffect(Unit) {
        weatherScreenViewModel.getWeather()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        MainWeatherScreen(weatherScreenViewModel, currentTime.longValue)
    }

}

fun formatUnixTime(unixTime: Long): String {
    val date = Date(unixTime * 1000)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}

fun formatUnixTime2(unixTime: Long): String {
    val date = Date(unixTime)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherScreen(
        hiltViewModel(),
        PaddingValues()
    )
}