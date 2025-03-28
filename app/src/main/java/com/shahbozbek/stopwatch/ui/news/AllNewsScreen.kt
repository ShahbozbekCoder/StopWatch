package com.shahbozbek.stopwatch.ui.news

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahbozbek.stopwatch.utils.Result
import kotlinx.coroutines.launch

@Composable
fun AllNewsScreen(
    newsScreenViewModel: NewsScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        newsScreenViewModel.getNews()
    }
    val newsState = newsScreenViewModel.newsData.collectAsState()
    val listOfCategory = listOf(
        "General",
        "Business",
        "Entertainment",
        "Health",
        "Science",
        "Sports",
        "Technology"
    )
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val scope = rememberCoroutineScope()
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
                items(listOfCategory.size) { index ->
                    FilterChip(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            scope.launch {
                                newsScreenViewModel.getNews(listOfCategory[index])
                            }
                        },
                        label = {
                            Text(
                                text = listOfCategory[index],
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(50),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.Cyan,
                            disabledContainerColor = Color.LightGray,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (newsState.value) {
                is Result.Error -> {
                    Text(text = newsState.value.toString())
                }

                is Result.Loading -> CircularProgressIndicator()
                is Result.Success -> {
                    val data = (newsState.value as Result.Success).data?.articles
                    LazyColumn {
                        items(data?.size ?: 0) { idx ->
                            NewsItem(
                                newsItem = data!![idx],
                                onClick = {
                                    val encodedUrl = Uri.encode(data[idx].url)
                                    navController.navigate("news_detail/$encodedUrl")
                                    newsScreenViewModel.setFavourite(data[idx])
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}