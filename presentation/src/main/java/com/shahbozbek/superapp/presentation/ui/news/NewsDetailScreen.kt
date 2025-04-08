package com.shahbozbek.superapp.presentation.ui.news

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsDetailScreen(
    newsUrl: String?,
    newsDetailScreenViewModel: NewsScreenViewModel = hiltViewModel<NewsScreenViewModel>()
) {
    val context = LocalContext.current
    val decodedUrl = newsUrl?.let {
        Uri.decode(it)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val favourite = newsDetailScreenViewModel.getArticle()
                favourite?.let {

                    newsDetailScreenViewModel.insertFavouriteArticle(it)
//                    if (isSaved) {
//                        Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(context, "Already in favourites", Toast.LENGTH_SHORT).show()
//                    }

                } ?: Toast.makeText(context, "No news available", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            decodedUrl?.let {
                AndroidView(
                    factory = { myContext ->
                        WebView(myContext).apply {
                            webViewClient = WebViewClient()
                            loadUrl(it)
                        }
                    },
                    update = {
                        it.loadUrl(decodedUrl)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Text(text = "No news URL available", fontSize = 18.sp, color = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsDetailScreenPreview() {
    NewsDetailScreen(
        newsUrl = null,
        newsDetailScreenViewModel = hiltViewModel()
    )
}