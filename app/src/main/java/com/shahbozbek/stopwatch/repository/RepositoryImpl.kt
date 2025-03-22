package com.shahbozbek.stopwatch.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shahbozbek.stopwatch.data.models.weatherdata.WeatherData
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
    private val weatherApiInterface: WeatherApiInterface
): Repository {

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

        if (response.isSuccessful){

            val body = response.body()

            emit(body)

        } else {
            throw Exception(response.message())
        }

    }.catch {
        throw Exception(it.message)
    }.flowOn(Dispatchers.IO)

}