package com.shahbozbek.superapp.domain.repository

import com.shahbozbek.superapp.domain.model.newsData.Article
import com.shahbozbek.superapp.domain.model.newsData.FavouriteArticle
import com.shahbozbek.superapp.domain.model.weatherData.WeatherData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getTime(): Long

    suspend fun saveTime(time: Long)

    fun getWeather(): Flow<WeatherData?>

    suspend fun insertFavouriteArticle(article: Article)

    fun getFavouriteArticles(): Flow<List<FavouriteArticle>>

    suspend fun deleteFavouriteArticles(article: FavouriteArticle)

    suspend fun insertNewsData(articles: List<Article>)

    suspend fun getNewsData(category: String = "", isOnline: Boolean = true): Flow<List<Article>>

    suspend fun deleteAllNewsData()

}