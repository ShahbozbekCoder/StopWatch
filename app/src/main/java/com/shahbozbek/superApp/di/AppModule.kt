package com.shahbozbek.superApp.di

import android.content.Context
import androidx.room.Room
import com.shahbozbek.superApp.data.local.ArticleDatabase
import com.shahbozbek.superApp.data.remote.NewsApiInterface
import com.shahbozbek.superApp.data.remote.RetrofitBuilder
import com.shahbozbek.superApp.data.remote.WeatherApiInterface
import com.shahbozbek.superApp.data.repositoryImpl.RepositoryImpl
import com.shahbozbek.superapp.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @[Provides Singleton]
    fun provideDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            ArticleDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @[Provides Singleton]
    fun provideFavouritesDao(database: ArticleDatabase) = database.favouritesDao()

    @[Provides Singleton]
    fun provideArticleDao(database: ArticleDatabase) = database.articleDao()


    @[Provides Singleton]
    fun provideWeatherApi(): WeatherApiInterface {
        return RetrofitBuilder.apiInterFaceBuilder<WeatherApiInterface>(
            baseUrl = RetrofitBuilder.BASE_URL_FROM_WEATHER
        )
    }

    @[Provides Singleton]
    fun provideNewsApi(): NewsApiInterface {
        return RetrofitBuilder.apiInterFaceBuilder<NewsApiInterface>(
            baseUrl = RetrofitBuilder.BASE_URL_FROM_NEWS
        )
    }

    @[Provides Singleton]
    fun provideRepository(
        @ApplicationContext context: Context,
        weatherApiInterface: WeatherApiInterface,
        newsApiInterface: NewsApiInterface,
        articleDatabase: ArticleDatabase,
    ): Repository {
        return RepositoryImpl(
            weatherApiInterface = weatherApiInterface,
            context = context,
            newsApiInterface = newsApiInterface,
            articleDao = articleDatabase.articleDao(),
            favouritesDao = articleDatabase.favouritesDao()
        )
    }
}