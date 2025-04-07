package com.shahbozbek.superApp.ui.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahbozbek.superApp.R
import com.shahbozbek.superApp.data.models.newsdata.Article
import com.shahbozbek.superApp.data.models.newsdata.FavouriteArticle
import com.skydoves.landscapist.glide.GlideImage
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun NewsItem(
    onClick: () -> Unit = {},
    newsItem: Article? = null,
    favouriteArticle: FavouriteArticle? = null
) {
    val onItemClicked = remember {
        mutableIntStateOf(0)
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                onClick()
                onItemClicked.intValue = 1
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (isSystemInDarkTheme()) Color.DarkGray else Color.White)
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GlideImage(
                        imageModel = { (newsItem?.urlToImage ?: favouriteArticle?.urlToImage) },
                        modifier = Modifier
                            .height(100.dp)
                            .width(160.dp),
                        failure = {
                            Image(
                                painter = painterResource(R.drawable.no_image),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        },
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        },
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        (newsItem?.title ?: favouriteArticle?.title)?.let {
                            Text(
                                text = it,
                                maxLines = 2,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 16.sp,
                                color = if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        (newsItem?.description ?: favouriteArticle?.description)?.let {
                            Text(
                                text = it,
                                maxLines = 2,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp,
                                overflow = TextOverflow.Ellipsis,
                                color = if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        (newsItem?.publishedAt ?: favouriteArticle?.publishedAt)?.let {
                            Text(
                                text = formatPublishedDateLegacy(it),
                                maxLines = 1,
                                fontSize = 14.sp,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }

                    }
                }
                (newsItem?.source?.name ?: favouriteArticle?.source?.name)?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

private fun formatPublishedDateLegacy(dateString: String?): String {
    if (dateString.isNullOrBlank()) return ""
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(dateString) ?: return ""

        val outputFormat = SimpleDateFormat("d MMMM yyyy, HH:mm", Locale("eng"))
        outputFormat.format(date)
    } catch (e: Exception) {
        ""
    }
}
