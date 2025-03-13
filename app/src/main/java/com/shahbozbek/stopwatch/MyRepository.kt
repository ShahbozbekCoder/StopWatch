package com.shahbozbek.stopwatch

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "stopWatch_prefs")

class MyRepository private constructor(private val context: Context) {

    companion object {
        val TIME = longPreferencesKey("time")

        @Volatile
        private var instance: MyRepository? = null

        fun getInstance(context: Context): MyRepository {
            return instance ?: synchronized(this) {
                instance ?: MyRepository(context).also { instance = it }
            }
        }
    }

    suspend fun saveTime(time: Long) {
        context.dataStore.edit { prefs ->
            prefs[TIME] = time
        }
    }

    suspend fun getTime(): Long {
        val prefs = context.dataStore.data.first()[TIME]
        return prefs ?: 0L
    }

}