package com.shahbozbek.superApp.domain.repository

import com.shahbozbek.superApp.domain.model.newsData.FavouriteArticle
import com.shahbozbek.superApp.domain.model.newsData.Article
import com.shahbozbek.superApp.domain.model.weatherData.WeatherData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getTime(): Long

    suspend fun saveTime(time: Long)

    fun getWeather(): Flow<WeatherData?>

    suspend fun insertFavouriteArticle(articleDto: Article)

    fun getFavouriteArticles(): Flow<List<FavouriteArticle>>

    suspend fun deleteFavouriteArticles(article: FavouriteArticle)

    suspend fun insertNewsData(newsData: List<Article>)

    suspend fun getNewsData(category: String = ""): Flow<List<Article>>

    suspend fun deleteAllNewsData()

}