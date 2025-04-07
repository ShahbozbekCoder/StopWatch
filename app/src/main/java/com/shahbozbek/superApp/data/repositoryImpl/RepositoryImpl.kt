package com.shahbozbek.superApp.data.repositoryImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shahbozbek.superApp.data.local.ArticleDao
import com.shahbozbek.superApp.data.local.FavouritesDao
import com.shahbozbek.superApp.data.models.newsDataDto.toDomain
import com.shahbozbek.superApp.data.models.weatherDataDto.toDomain
import com.shahbozbek.superApp.domain.model.newsData.FavouriteArticle
import com.shahbozbek.superApp.data.remote.NetworkUtils
import com.shahbozbek.superApp.data.remote.NewsApiInterface
import com.shahbozbek.superApp.data.remote.WeatherApiInterface
import com.shahbozbek.superApp.domain.repository.Repository
import com.shahbozbek.superApp.data.repositoryImpl.RepositoryImpl.Companion.DATA_STORE_NAME
import com.shahbozbek.superApp.domain.model.newsData.Article
import com.shahbozbek.superApp.domain.model.newsData.toDto
import com.shahbozbek.superApp.domain.model.newsData.toEntity
import com.shahbozbek.superApp.domain.model.weatherData.WeatherData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

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
        const val DATA_STORE_NAME = "stopWatch_prefs"
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

            val body = response.body()?.toDomain()

            emit(body)

        } else {
            throw Exception(response.message())
        }

    }.catch {
        throw Exception()
    }.flowOn(Dispatchers.IO)

    override suspend fun insertFavouriteArticle(myArticles: Article) {
        favouritesDao
            .insertFavouriteArticle(myArticles.toEntity())
    }

    override fun getFavouriteArticles(): Flow<List<FavouriteArticle>> = flow {
        emit(
            favouritesDao.getFavouriteArticles().map { it.toDomain() }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteFavouriteArticles(article: FavouriteArticle) {
        favouritesDao.deleteFavouriteArticles(article.toDto())
    }

    override suspend fun insertNewsData(insertArticles: List<Article>) {
        articleDao.insertNewsData(insertArticles.map { it.toDto() })
    }

    override suspend fun getNewsData(category: String): Flow<List<Article>> = flow {
        if (networkUtils.isNetworkAvailable()) {

            val response = newsApiInterface.getNews(category = category)

            if (response.isSuccessful) {
                val articles = response.body()!!.articleDtos

                articleDao.deleteAllNewsData()
                articleDao.insertNewsData(articles)

                emit(articles.map { it.toDomain() })
            } else {
                emit(articleDao.getNewsData().map { it.toDomain() })
            }
        } else {
            emit(articleDao.getNewsData().map { it.toDomain() })
        }
    }.catch {
        emit(articleDao.getNewsData().map { it.toDomain() })
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteAllNewsData() {
        articleDao.deleteAllNewsData()
    }

}