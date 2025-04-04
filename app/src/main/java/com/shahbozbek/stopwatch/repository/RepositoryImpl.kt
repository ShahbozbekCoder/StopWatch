package com.shahbozbek.stopwatch.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shahbozbek.stopwatch.data.local.ArticleDao
import com.shahbozbek.stopwatch.data.local.FavouritesDao
import com.shahbozbek.stopwatch.data.models.newsdata.Article
import com.shahbozbek.stopwatch.data.models.newsdata.FavouriteArticle
import com.shahbozbek.stopwatch.data.models.newsdata.NewsData
import com.shahbozbek.stopwatch.data.models.weatherdata.WeatherData
import com.shahbozbek.stopwatch.data.remote.NetworkUtils
import com.shahbozbek.stopwatch.data.remote.NewsApiInterface
import com.shahbozbek.stopwatch.data.remote.WeatherApiInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "stopWatch_prefs")

class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val weatherApiInterface: WeatherApiInterface,
    private val newsApiInterface: NewsApiInterface,
    private val networkUtils: NetworkUtils,
    private val articleDao: ArticleDao,
    private val favouritesDao: FavouritesDao
) : Repository {

    companion object {
        val TIME = longPreferencesKey("time")
    }

    override suspend fun saveTime(time: Long) {
        context.dataStore.edit { prefs ->
            prefs[TIME] = time
        }
    }

    override suspend fun getTime(): Long {
        val prefs = context.dataStore.data.first()[TIME]
        return prefs ?: 0L
    }

    override fun getWeather(): Flow<WeatherData?> = flow {

        val response = weatherApiInterface.getWeather()

        if (response.isSuccessful) {

            val body = response.body()

            emit(body)

        } else {
            throw Exception(response.message())
        }

    }.catch {
        throw Exception(it.message)
    }.flowOn(Dispatchers.IO)

    override fun getNews(category: String): Flow<NewsData?> = flow {

        val response = newsApiInterface.getNews(category = category)

        Log.d("RepositoryImpl", "getNews: $response")

        if (response.isSuccessful) {

            val body = response.body()

            Log.d("RepositoryImpl", "getNews: $body")

            emit(body)

        } else {
            Log.d("RepositoryImpl", "getNews: ${response.message()}")
            throw Exception(response.message())
        }

    }.catch {
        throw Exception(it.message)
    }.flowOn(Dispatchers.IO)

    override suspend fun insertFavouriteArticle(article: Article) {
        favouritesDao
            .insertFavouriteArticle(FavouriteArticle.fromArticle(article))
    }

    override fun getFavouriteArticles(): Flow<List<FavouriteArticle>> = flow {
        emit(favouritesDao.getFavouriteArticles())
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteFavouriteArticles(article: FavouriteArticle) {
        favouritesDao.deleteFavouriteArticles(article)
    }

    override suspend fun insertNewsData(newsData: List<Article>) {
        articleDao.insertNewsData(newsData)
    }

    override suspend fun getNewsData(category: String): Flow<List<Article>> = flow {
        if (networkUtils.isNetworkAvailable()) {

            val response = newsApiInterface.getNews(category = category)

            if (response.isSuccessful) {
                val articles = response.body()!!.articles

                articleDao.deleteAllNewsData()
                articleDao.insertNewsData(articles)

                emit(articles)
            } else {
                emit(articleDao.getNewsData())
            }
        } else {
            emit(articleDao.getNewsData())
        }
    }.catch {
        emit(articleDao.getNewsData())
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteAllNewsData() {
        articleDao.deleteAllNewsData()
    }

}