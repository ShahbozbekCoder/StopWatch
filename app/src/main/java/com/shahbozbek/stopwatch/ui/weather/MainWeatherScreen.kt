package com.shahbozbek.stopwatch.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shahbozbek.stopwatch.R
import com.shahbozbek.stopwatch.utils.Constants
import com.shahbozbek.stopwatch.utils.Constants.CITY_NAME
import com.shahbozbek.stopwatch.utils.Constants.HUMIDITY
import com.shahbozbek.stopwatch.utils.Constants.LAST_UPDATE
import com.shahbozbek.stopwatch.utils.Constants.PRESSURE
import com.shahbozbek.stopwatch.utils.Constants.SUNRISE
import com.shahbozbek.stopwatch.utils.Constants.SUNSET
import com.shahbozbek.stopwatch.utils.Constants.WIND_SPEED
import com.shahbozbek.stopwatch.utils.Constants.backgrounds
import com.shahbozbek.stopwatch.utils.Constants.clouds
import com.shahbozbek.stopwatch.utils.Constants.rain
import com.shahbozbek.stopwatch.utils.Constants.snow
import com.shahbozbek.stopwatch.utils.Constants.sunny

@Composable
fun MainWeatherScreen(
    weatherScreenViewModel: WeatherScreenViewModel, myTime: Long
) {

    val currentTime = remember {
        mutableLongStateOf(myTime)
    }

    val state = weatherScreenViewModel.state.collectAsState()

    val conditions = state.value.weatherData?.weather?.firstOrNull()?.main

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (clouds.contains(conditions)) {
                    backgrounds[0]
                } else if (rain.contains(conditions)) {
                    backgrounds[1]
                } else if (snow.contains(conditions)) {
                    backgrounds[2]
                } else if (sunny.contains(conditions)) {
                    backgrounds[3]
                } else {
                    backgrounds[4]
                }
            )
            .verticalScroll(
                state = rememberScrollState()
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(
                onClick = {
                    weatherScreenViewModel.getWeather()
                    currentTime.longValue = System.currentTimeMillis()
                }, modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(48.dp)
                    .padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bg_refresh),
                    contentDescription = "Refresh",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            if (state.value.isLoading) {
                CircularProgressIndicator(
                    color = Color.White, modifier = Modifier.align(Alignment.Center)
                )
            }

            if (state.value.error != null) {
                Text(
                    text = state.value.error ?: "",
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(top = 65.dp),
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif
                )
            }
        }
        Text(
            text = CITY_NAME, color = Color.White, fontSize = 48.sp, fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            state.value.weatherData?.let {
                Text(
                    text = "${(it.main.temp - 273).toInt()} ºC",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = it.weather.firstOrNull()?.main ?: "",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontFamily = FontFamily.Serif
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        if (clouds.contains(conditions)) {
            Image(
                painter = painterResource(id = R.drawable.cloudly),
                contentDescription = clouds[0],
                modifier = Modifier.width(150.dp)
            )

        }
        if (rain.contains(conditions)) {
            Image(
                painter = painterResource(id = R.drawable.rainy),
                contentDescription = rain[0],
                modifier = Modifier.width(150.dp)
            )
        }
        if (snow.contains(conditions)) {
            Image(
                painter = painterResource(id = R.drawable.snowy),
                contentDescription = snow[0],
                modifier = Modifier.width(150.dp)
            )
        }
        if (sunny.contains(conditions)) {
            Image(
                painter = painterResource(id = R.drawable.sunny),
                contentDescription = snow[0],
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = HUMIDITY,
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = HUMIDITY,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            state.value.weatherData?.let {
                Text(
                    text = "${it.main.humidity}%",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.wind_speed),
                contentDescription = WIND_SPEED,
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = WIND_SPEED,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            state.value.weatherData?.let {
                Text(
                    text = "${it.wind.speed.toInt()} km/h",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = PRESSURE,
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = PRESSURE,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            state.value.weatherData?.let {
                Text(
                    text = "${it.main.pressure} mb",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = SUNRISE,
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = SUNRISE,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            state.value.weatherData?.let {
                Text(
                    text = formatUnixTime(it.sys.sunrise.toLong()),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = SUNSET,
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = SUNSET,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            state.value.weatherData?.let {
                Text(
                    text = formatUnixTime(it.sys.sunset.toLong()),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .weight(1f)
        )
        val myCurrentTime = formatUnixTime2(currentTime.longValue)
        Text(
            text = LAST_UPDATE + myCurrentTime,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainWeatherScreenPreview() {
    MainWeatherScreen(
        hiltViewModel(), myTime = 0L
    )
}
