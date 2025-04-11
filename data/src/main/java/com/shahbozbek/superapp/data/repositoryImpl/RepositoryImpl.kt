package com.shahbozbek.superapp.data.repositoryImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shahbozbek.superapp.data.dto.newsDataDto.toDomain
import com.shahbozbek.superapp.data.dto.toDto
import com.shahbozbek.superapp.data.dto.toFavouriteArticleDto
import com.shahbozbek.superapp.data.dto.weatherDataDto.toDomain
import com.shahbozbek.superapp.data.local.dao.ArticleDao
import com.shahbozbek.superapp.data.local.dao.FavouritesDao
import com.shahbozbek.superapp.data.remote.NewsApiInterface
import com.shahbozbek.superapp.data.remote.WeatherApiInterface
import com.shahbozbek.superapp.data.repositoryImpl.RepositoryImpl.Companion.DATA_STORE_NAME
import com.shahbozbek.superapp.domain.model.newsData.Article
import com.shahbozbek.superapp.domain.model.newsData.FavouriteArticle
import com.shahbozbek.superapp.domain.model.weatherData.WeatherData
import com.shahbozbek.superapp.domain.repository.Repository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val weatherApiInterface: WeatherApiInterface,
    private val newsApiInterface: NewsApiInterface,
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

    override suspend fun insertFavouriteArticle(article: Article) {
        favouritesDao
            .insertFavouriteArticle(article.toFavouriteArticleDto())
    }

    override fun getFavouriteArticles(): Flow<List<FavouriteArticle>> = flow {
        emit(
            favouritesDao.getFavouriteArticles().map {
                it.toDomain()
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteFavouriteArticles(article: FavouriteArticle) =
        withContext(Dispatchers.IO) {
            favouritesDao.deleteFavouriteArticles(article.toDto())
        }

    override suspend fun insertNewsData(articles: List<Article>) {
        articleDao.insertNewsData(articles.map { it.toDto() })
    }

    override suspend fun getNewsData(category: String, isOnline: Boolean): Flow<List<Article>> =
        flow {
            if (isOnline) {

                val response = newsApiInterface.getNews(category = category)

                if (response.isSuccessful) {
                    val articles = response.body()!!.articles.map { it.toDomain() }

                    articleDao.deleteAllNewsData()
                    articleDao.insertNewsData(articles.map { it.toDto() })

                    emit(articles)
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