package com.shahbozbek.stopwatch.ui.news

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    newsScreenViewModel: NewsScreenViewModel,
    navController: NavController
) {
    val favouriteNews by newsScreenViewModel.getFavouriteNews()
        .collectAsState(initial = emptyList())

    Log.d("TAG", "FavouritesScreen: $favouriteNews")
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(favouriteNews.size) { idx ->

                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { newValue ->
                        if (newValue == SwipeToDismissBoxValue.EndToStart) {
                            newsScreenViewModel.deleteFavouriteArticle(favouriteNews[idx])
                            true
                        } else {
                            false
                        }
                    }
                )
                SwipeToDismissBox(
                    state = dismissState,
                    modifier = Modifier.fillMaxSize(),
                    backgroundContent = {

                    },
                ) {
                    NewsItem(
                        newsItem = favouriteNews[idx],
                        onClick = {
                            val encodedUrl = Uri.encode(favouriteNews[idx].url)
                            navController.navigate("news_detail/$encodedUrl")
                        }
                    )
                }
            }
        }
    }
}