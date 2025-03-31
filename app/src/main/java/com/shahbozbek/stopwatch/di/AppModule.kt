package com.shahbozbek.stopwatch.di

import android.content.Context
import androidx.room.Room
import com.shahbozbek.stopwatch.data.database.ArticleDatabase
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

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @[Provides Reusable]
    fun provideDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "article_database"
        ).build()
    }

    @Provides
    fun provideDao(database: ArticleDatabase) = database.articleDao

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
        articleDatabase: ArticleDatabase
    ): Repository {
        return RepositoryImpl(
            weatherApiInterface = weatherApiInterface,
            context = context,
            newsApiInterface = newsApiInterface,
            articleDatabase = articleDatabase
        )
    }

}