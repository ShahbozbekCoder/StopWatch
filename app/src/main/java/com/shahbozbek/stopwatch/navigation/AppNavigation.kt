package com.shahbozbek.stopwatch.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shahbozbek.stopwatch.ui.news.AllNewsScreen
import com.shahbozbek.stopwatch.ui.news.FavouritesScreen
import com.shahbozbek.stopwatch.ui.news.NewsDetailScreen
import com.shahbozbek.stopwatch.ui.news.NewsScreen
import com.shahbozbek.stopwatch.ui.news.NewsScreenViewModel
import com.shahbozbek.stopwatch.ui.stopwatch.StopWatchScreen
import com.shahbozbek.stopwatch.ui.weather.WeatherScreen

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

                    Screens.NewsScreen -> {
                        NewsScreen(paddingValues = paddingValues)
                    }
                }
            }

        }
    }
}

@Composable
fun NewsNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    newsScreenViewModel: NewsScreenViewModel = hiltViewModel()
) {

    NavHost(
        navController = navController,
        modifier = Modifier.padding(paddingValues),
        startDestination = "all_news_screen"
    ) {

        composable("all_news_screen") {
            AllNewsScreen(
                navController = navController,
                newsScreenViewModel = newsScreenViewModel
            )
        }

        composable(
            route = "news_detail/{newsUrl}",
            arguments = listOf(
                navArgument(
                    name = "newsUrl", builder = { type = NavType.StringType },
                )
            )
        ) { backStackEntry ->
            val newsUrl = backStackEntry.arguments?.getString("newsUrl")
            NewsDetailScreen(
                newsUrl,
                newsScreenViewModel
            )
        }

        composable("favourites_screen") {
            FavouritesScreen(
                navController = navController,
                newsScreenViewModel = newsScreenViewModel
            )
        }
    }

}