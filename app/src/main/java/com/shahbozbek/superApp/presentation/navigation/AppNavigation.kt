package com.shahbozbek.superApp.presentation.navigation

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
import com.shahbozbek.superApp.presentation.navigation.ScreenNames.ALL_NEWS_SCREEN
import com.shahbozbek.superApp.presentation.navigation.ScreenNames.ARGUMENT_NEWS_URL
import com.shahbozbek.superApp.presentation.navigation.ScreenNames.FAVOURITES_SCREEN
import com.shahbozbek.superApp.presentation.navigation.ScreenNames.NEWS_DETAIL_SCREEN
import com.shahbozbek.superApp.presentation.ui.news.AllNewsScreen
import com.shahbozbek.superApp.presentation.ui.news.FavouritesScreen
import com.shahbozbek.superApp.presentation.ui.news.NewsDetailScreen
import com.shahbozbek.superApp.presentation.ui.news.NewsScreen
import com.shahbozbek.superApp.presentation.ui.news.NewsScreenViewModel
import com.shahbozbek.superApp.presentation.ui.stopwatch.StopWatchScreen
import com.shahbozbek.superApp.presentation.ui.weather.WeatherScreen

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
        startDestination = ALL_NEWS_SCREEN
    ) {

        composable(ALL_NEWS_SCREEN) {
            AllNewsScreen(
                navController = navController,
                newsScreenViewModel = newsScreenViewModel
            )
        }

        composable(
            route = NEWS_DETAIL_SCREEN,
            arguments = listOf(
                navArgument(
                    name = ARGUMENT_NEWS_URL, builder = { type = NavType.StringType },
                )
            )
        ) { backStackEntry ->
            val newsUrl = backStackEntry.arguments?.getString(ARGUMENT_NEWS_URL)
            NewsDetailScreen(
                newsUrl = newsUrl,
                newsDetailScreenViewModel = newsScreenViewModel
            )
        }

        composable(FAVOURITES_SCREEN) {
            FavouritesScreen(
                navController = navController,
                newsScreenViewModel = newsScreenViewModel
            )
        }
    }

}

object ScreenNames {
    const val ALL_NEWS_SCREEN = "all_news_screen"
    const val NEWS_DETAIL_SCREEN = "news_detail/{newsUrl}"
    const val FAVOURITES_SCREEN = "favourites_screen"
    const val ARGUMENT_NEWS_URL = "newsUrl"
}