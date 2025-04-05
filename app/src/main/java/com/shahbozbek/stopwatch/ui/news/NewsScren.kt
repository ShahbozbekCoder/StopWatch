package com.shahbozbek.stopwatch.ui.news

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shahbozbek.stopwatch.navigation.NewsNavigation

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsScreen(
    paddingValues: PaddingValues,
) {

    val items = listOf(Pair("Home", "all_news_screen"), Pair("Favourites", "favourites_screen"))
    val icons = listOf(Icons.Default.Home, Icons.Default.Favorite)
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {

            val navigationState = navController.currentBackStackEntryAsState()
            val currentRoute = navigationState.value?.destination?.route

            if (currentRoute in items.map { it.second }) {
                NavigationBar(modifier = Modifier.fillMaxWidth()) {

                    items.forEachIndexed { index, pair ->
                        NavigationBarItem(
                            icon = { Icon(icons[index], contentDescription = null) },
                            label = { Text(pair.first) },
                            onClick = {
                                navController.navigate(pair.second) {
                                    popUpTo("all_news_screen") {
                                        saveState = true
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            },
                            selected = currentRoute == pair.second
                        )
                    }
                }
            }
        }
    ) {
        NewsNavigation(navController = navController, paddingValues = paddingValues)
    }
}


@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    NewsScreen(
        paddingValues = PaddingValues()
    )
}