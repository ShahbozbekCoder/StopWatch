package com.shahbozbek.superapp.data.remote

import com.shahbozbek.superapp.data.dto.newsDataDto.NewsDataDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_FROM_NEWS = "9523c2528f664c81859233987155bc42"

interface NewsApiInterface {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "us",
        @Query("category") category: String = "",
        @Query("apiKey") apiKey: String = API_FROM_NEWS
    ): Response<NewsDataDto>

}