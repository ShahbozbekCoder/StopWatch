package com.shahbozbek.superApp.ui.news

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun FavouritesScreen(
    newsScreenViewModel: NewsScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val favouriteNews by newsScreenViewModel.getFavouriteNews()
        .collectAsState(initial = emptyList())

    var newsList by remember {
        mutableStateOf(favouriteNews)
    }

    LaunchedEffect(favouriteNews) {
        newsList = favouriteNews
    }

    Log.d("TAG", "FavouritesScreen: $favouriteNews")
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(newsList, key = { it.url!! }) { item ->

                val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = { newValue ->
                    if (newValue == SwipeToDismissBoxValue.EndToStart || newValue == SwipeToDismissBoxValue.StartToEnd) {
                        newsScreenViewModel.deleteFavouriteArticle(item)

                        newsList = newsList.filter { it.url != item.url }
                        true
                    } else {
                        false
                    }
                })
                SwipeToDismissBox(
                    state = dismissState,
                    modifier = Modifier.fillMaxSize(),
                    backgroundContent = {

                    },
                ) {
                    NewsItem(favouriteArticle = item, onClick = {
                        val encodedUrl = Uri.encode(item.url)
                        navController.navigate("news_detail/$encodedUrl")
                    })
                }
            }
        }
        if (newsList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Favourites",
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    fontSize = 32.sp
                )
            }
        }
    }
}