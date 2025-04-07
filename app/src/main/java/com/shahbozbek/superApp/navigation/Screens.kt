package com.shahbozbek.superApp.navigation

import com.shahbozbek.superApp.R

enum class Screens(val route: String) {
    StopWatchScreen("stopwatch_screen"),
    WeatherScreen("weather_screen"),
    NewsScreen("news_screen")
}

data class NavItems(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: String
)

val listOfNavItems = listOf(
    NavItems(
        title = "Stopwatch",
        selectedIcon = R.drawable.selected_stopwatch,
        unselectedIcon = R.drawable.unselected_stopwatch,
        route = Screens.StopWatchScreen.route
    ),
    NavItems(
        title = "Weather",
        selectedIcon = R.drawable.selected_weather,
        unselectedIcon = R.drawable.unselected_weather,
        route = Screens.WeatherScreen.route
    ),
    NavItems(
        title = "News",
        selectedIcon = R.drawable.selected_news,
        unselectedIcon = R.drawable.unselected_news,
        route = Screens.NewsScreen.route
    )
)