package com.shahbozbek.superapp.presentation.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    fun isNetworkAvailable(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activityNetwork = connectivityManager.activeNetworkInfo

        return activityNetwork != null && activityNetwork.isConnected
    }
}