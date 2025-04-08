package com.shahbozbek.superApp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    const val BASE_URL_FROM_WEATHER = "https://api.openweathermap.org/data/2.5/"
    const val BASE_URL_FROM_NEWS = "https://newsapi.org/v2/"

    inline fun <reified T> apiInterFaceBuilder(
        baseUrl: String
    ): T {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val interceptor = HttpInterceptor()

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(T::class.java)
    }
}