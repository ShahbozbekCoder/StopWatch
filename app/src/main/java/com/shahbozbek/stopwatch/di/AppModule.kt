package com.shahbozbek.stopwatch.di

import android.content.Context
import androidx.room.Room
import com.shahbozbek.stopwatch.data.local.ArticleDatabase
import com.shahbozbek.stopwatch.data.remote.HttpInterceptor
import com.shahbozbek.stopwatch.data.remote.NetworkUtils
import com.shahbozbek.stopwatch.data.remote.WeatherApiInterface
import com.shahbozbek.stopwatch.data.remote.NewsApiInterface
import com.shahbozbek.stopwatch.data.remote.RetrofitBuilder
import com.shahbozbek.stopwatch.repository.Repository
import com.shahbozbek.stopwatch.repository.RepositoryImpl
import com.shahbozbek.stopwatch.ui.theme.ThemePreferences
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @[Provides Reusable]
    fun provideDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            ArticleDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @[Provides Reusable]
    fun provideFavouritesDao(database: ArticleDatabase) = database.favouritesDao()

    @[Provides Reusable]
    fun provideArticleDao(database: ArticleDatabase) = database.articleDao()

    @[Provides Reusable]
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkUtils {
        return NetworkUtils(context)
    }

    @[Provides Reusable]
    fun provideWeatherApi(): WeatherApiInterface {
        return RetrofitBuilder.apiInterFaceBuilder<WeatherApiInterface>(
            baseUrl = RetrofitBuilder.BASE_URL_FROM_WEATHER
        )
    }

    @[Provides Reusable]
    fun provideNewsApi(): NewsApiInterface {
        return RetrofitBuilder.apiInterFaceBuilder<NewsApiInterface>(
            baseUrl = RetrofitBuilder.BASE_URL_FROM_NEWS
        )
    }

    @[Provides Reusable]
    fun provideThemePreferences(@ApplicationContext context: Context): ThemePreferences {
        return ThemePreferences(context)
    }

    @[Provides Reusable]
    fun provideRepository(
        @ApplicationContext context: Context,
        weatherApiInterface: WeatherApiInterface,
        newsApiInterface: NewsApiInterface,
        articleDatabase: ArticleDatabase,
        networkUtils: NetworkUtils
    ): Repository {
        return RepositoryImpl(
            weatherApiInterface = weatherApiInterface,
            context = context,
            newsApiInterface = newsApiInterface,
            networkUtils = networkUtils,
            articleDao = articleDatabase.articleDao(),
            favouritesDao = articleDatabase.favouritesDao()
        )
    }

}