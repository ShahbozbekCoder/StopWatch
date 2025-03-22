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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shahbozbek.stopwatch.R

@Composable
fun MainWeatherScreen(
    weatherScreenViewModel: WeatherScreenViewModel,
    myTime: Long
) {

    val currentTime = remember {
        mutableLongStateOf(myTime)
    }

    val backgrounds = listOf(
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF7BCAEE),
                Color(0xFF2F4042)
            )
        ),
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF3D6070),
                Color(0xFF576D70)
            )
        ),
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFC2E1EF),
                Color(0xFF8BBDC0)
            )
        ),
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF46B5E7),
                Color(0xFF1BD9F1)
            )
        ),
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF8FCEEE),
                Color(0xFFC6D2D3)
            )
        ),
    )

    val state = weatherScreenViewModel.state.collectAsState()

    val conditions = state.value.weatherData?.weather?.firstOrNull()?.main

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (conditions) {
                    "Haze", "Clouds", "Foggy", "Partly Clouds", "Overcast", "Mist" -> {
                        backgrounds[0]
                    }

                    "Rain", "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> {
                        backgrounds[1]
                    }

                    "Snow", "Light Snow", "Moderate Snow", "Heaby Snow", "Blizzard" -> {
                        backgrounds[2]
                    }

                    "Sunny", "Clear", "Clear sky" -> {
                        backgrounds[3]
                    }

                    else -> {
                        backgrounds[4]
                    }
                }
            )
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(
                onClick = {
                    weatherScreenViewModel.getWeather()
                    currentTime.longValue = System.currentTimeMillis()
                },
                modifier = Modifier
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
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
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
            text = "Tashkent",
            color = Color.White,
            fontSize = 48.sp,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(16.dp))
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
        Spacer(modifier = Modifier.height(32.dp))
        when (conditions) {
            "Haze", "Clouds", "Foggy", "Partly Clouds", "Overcast", "Mist" -> {
                Image(
                    painter = painterResource(id = R.drawable.cloudly),
                    contentDescription = "Cloudy",
                    modifier = Modifier.width(150.dp)
                )

            }
            "Rain", "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> {
                Image(
                    painter = painterResource(id = R.drawable.rainy),
                    contentDescription = "Rain",
                    modifier = Modifier.width(150.dp)
                )
            }
            "Snow", "Light Snow", "Moderate Snow", "Heaby Snow", "Blizzard" -> {
                Image(
                    painter = painterResource(id = R.drawable.snowy),
                    contentDescription = "Snow",
                    modifier = Modifier.width(150.dp)
                )
            }
            "Sunny", "Clear", "Clear sky" -> {
                Image(
                    painter = painterResource(id = R.drawable.sunny),
                    contentDescription = "Sunny",
                    modifier = Modifier.width(150.dp)
                    )
            }
            else -> {
                Image(
                    painter = painterResource(id = R.drawable.sunny),
                    contentDescription = "Sunny",
                    modifier = Modifier.width(150.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity",
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Humidity:",
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
                contentDescription = "Wind",
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Wind Speed:",
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
                contentDescription = "Pressure",
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Pressure:",
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
                contentDescription = "Sunrise",
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sunrise:",
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
                contentDescription = "Sunset",
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sunset:",
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
        Text(
            text = "Last update: ${formatUnixTime2(currentTime.longValue)}",
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
        hiltViewModel(),
        0L
    )
}
