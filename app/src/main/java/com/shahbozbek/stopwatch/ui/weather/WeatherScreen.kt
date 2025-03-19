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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.shahbozbek.stopwatch.utils.Result

@Composable
fun WeatherScreen(
    weatherScreenViewModel: WeatherScreenViewModel = hiltViewModel<WeatherScreenViewModel>()
) {
    
    LaunchedEffect (Unit){
        weatherScreenViewModel.getWeather()
    }
    
    val weatherState = weatherScreenViewModel.weatherData.collectAsState()

    when (weatherState.value) {
        is Result.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${(weatherState.value as Result.Error).message}",
                    color = Color.Red
                )
            }
        }
        is Result.Loading -> CircularProgressIndicator()
        is Result.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF46B5E7),
                                Color(0xFF1BD9F1)
                            )
                        )
                    )
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                IconButton(
                    onClick = { },
                    modifier = Modifier.align(Alignment.End)
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
                    Text(
                        text = "20°C",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    Text(
                        text = "Sunny",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    painter = painterResource(id = R.drawable.sunny),
                    contentDescription = "Sunny",
                    modifier = Modifier.width(150.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
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
                    Text(
                        text = "80%",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
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
                    Text(
                        text = "12 km/h",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
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
                    Text(
                        text = "1000 hpa",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherScreen()
}