package com.shahbozbek.stopwatch.ui.news

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahbozbek.stopwatch.utils.Result
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNewsScreen(
    newsScreenViewModel: NewsScreenViewModel = hiltViewModel(),
    navController: NavController,
) {

    val newsState = newsScreenViewModel.newsData.collectAsState()
    val listOfCategory by newsScreenViewModel.listOfCategory.collectAsState()
    val selectedCategory by newsScreenViewModel.selectedCategory.collectAsState("General")
    val scope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()
    val isRefreshing = remember {
        mutableStateOf(newsState.value is Result.Loading)
    }

    LaunchedEffect(newsState.value) {
        Log.d("VVVVV", "getNewsData: Launched effect")
        if (newsState.value is Result.Success) {
            isRefreshing.value = newsState.value is Result.Loading
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                items(listOfCategory) { item ->
                    FilterChip(
                        selected = selectedCategory == item,
                        onClick = {
                            scope.launch {
                                newsScreenViewModel.getNewsData(item)
                            }
                        },
                        label = {
                            Text(
                                text = item,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(50),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.Cyan,
                            disabledContainerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            PullToRefreshBox(
                state = refreshState,
                onRefresh = {
                    newsScreenViewModel.getNewsData(selectedCategory)
                },
                isRefreshing = newsState.value is Result.Loading,
                modifier = Modifier.weight(1f)
            ) {
                when (newsState.value) {
                    is Result.Error -> {
                        Text(text = newsState.value.toString())
                    }

                    is Result.Loading -> {}
                    is Result.Success -> {

                        LazyColumn {
                            (newsState.value as? Result.Success)?.let {
                                items(it.data.articles) { item ->
                                    NewsItem(
                                        newsItem = item,
                                        onClick = {
                                            val encodedUrl = Uri.encode(item.url)
                                            navController.navigate("news_detail/$encodedUrl")
                                            newsScreenViewModel.setFavourite(item)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}