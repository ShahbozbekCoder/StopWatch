package com.shahbozbek.stopwatch.data.remote

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUtils @Inject constructor (@ApplicationContext private val context: Context) {

    fun isNetworkAvailable(): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activityNetwork = connectivityManager.activeNetworkInfo

        return activityNetwork != null && activityNetwork.isConnected
    }
}