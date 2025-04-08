package com.shahbozbek.superapp.presentation.ui.news

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahbozbek.superapp.presentation.utils.NetworkUtils
import com.shahbozbek.superapp.presentation.utils.Results
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNewsScreen(
    newsScreenViewModel: NewsScreenViewModel = hiltViewModel<NewsScreenViewModel>(),
    navController: NavController
) {

    val newsState by newsScreenViewModel.newsData.collectAsState()
    val listOfCategory by newsScreenViewModel.listOfCategory.collectAsState()
    val selectedCategory by newsScreenViewModel.selectedCategory.collectAsState("General")
    val refreshState = rememberPullToRefreshState()
    val isRefreshing = newsState is Results.Loading
    val context = LocalContext.current

    val isOnline = NetworkUtils.isNetworkAvailable(context)
    if (!isOnline) {
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
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
                items(listOfCategory) { item ->
                    FilterChip(
                        selected = selectedCategory == item,
                        onClick = {
                            newsScreenViewModel.getNewsData(category = item, isOnline = isOnline)
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
                    if (!isOnline) {
                        scope.launch {
                            refreshState.snapTo(0f)
                        }
                        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                    } else newsScreenViewModel.getNewsData(
                        category = selectedCategory,
                        isOnline = isOnline
                    )
                },
                isRefreshing = isRefreshing,
                modifier = Modifier.weight(1f)
            ) {
                when (newsState) {
                    is Results.Error -> {
                        Text(text = newsState.toString())
                    }

                    is Results.Loading -> {}

                    is Results.Success -> {

                        val data = (newsState as Results.Success).data.articles

                        LazyColumn {
                            items(data) { item ->
                                NewsItem(
                                    newsItem = item,
                                    onClick = {
                                        val encodedUrl = Uri.encode(item.url)
                                        navController.navigate("news_detail/$encodedUrl")
                                        newsScreenViewModel.setArticle(item)
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