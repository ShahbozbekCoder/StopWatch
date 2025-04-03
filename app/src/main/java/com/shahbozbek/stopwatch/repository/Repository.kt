package com.shahbozbek.stopwatch.repository

import com.shahbozbek.stopwatch.data.models.newsdata.Article
import com.shahbozbek.stopwatch.data.models.newsdata.FavouriteArticle
import com.shahbozbek.stopwatch.data.models.newsdata.NewsData
import com.shahbozbek.stopwatch.data.models.weatherdata.WeatherData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getTime(): Long

    suspend fun saveTime(time: Long)

    fun getWeather(): Flow<WeatherData?>

    fun getNews(category: String = ""): Flow<NewsData?>

    suspend fun insertFavouriteArticle(article: Article)

    fun getFavouriteArticles(): Flow<List<FavouriteArticle>>

    suspend fun deleteFavouriteArticles(article: FavouriteArticle)

    suspend fun insertNewsData(newsData: List<Article>)

    suspend fun getNewsData(category: String = ""): Flow<List<Article>>

    suspend fun deleteAllNewsData()

}