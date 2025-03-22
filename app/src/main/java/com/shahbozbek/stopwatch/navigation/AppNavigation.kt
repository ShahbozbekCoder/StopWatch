package com.shahbozbek.stopwatch.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shahbozbek.stopwatch.ui.stopwatch.StopWatchScreen
import com.shahbozbek.stopwatch.ui.weather.WeatherScreen
import kotlin.system.exitProcess

@Composable
fun AppNavigation(navHostController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navHostController, startDestination = Screens.StopWatchScreen.route) {
        Screens.entries.forEach { screen ->
            composable(screen.route) {
                when (screen) {
                    Screens.StopWatchScreen -> {
                        StopWatchScreen(paddingValues = paddingValues)
                    }
                    Screens.WeatherScreen -> {
                        WeatherScreen(paddingValues = paddingValues)
                    }
                }
            }
        }
    }
}