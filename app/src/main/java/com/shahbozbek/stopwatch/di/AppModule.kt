package com.shahbozbek.stopwatch.di

import android.content.Context
import com.shahbozbek.stopwatch.data.remote.RetrofitBuilder
import com.shahbozbek.stopwatch.data.remote.WeatherApiInterface
import com.shahbozbek.stopwatch.repository.Repository
import com.shahbozbek.stopwatch.repository.RepositoryImpl
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
    fun provideGetWeatherApi(): WeatherApiInterface {
        return RetrofitBuilder.apiInterFaceBuilder()
    }

    @[Provides Reusable]
    fun provideRepository(
        @ApplicationContext context: Context,
        weatherApiInterface: WeatherApiInterface
    ): Repository {
        return RepositoryImpl(
            weatherApiInterface = weatherApiInterface,
            context = context
        )
    }

}