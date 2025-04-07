package com.shahbozbek.superApp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahbozbek.superApp.data.models.newsDataDto.FavouriteArticleDto

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteArticle(article: FavouriteArticleDto)

    @Query("SELECT * FROM favourite_table")
    fun getFavouriteArticles(): List<FavouriteArticleDto>

    @Delete
    suspend fun deleteFavouriteArticles(article: FavouriteArticleDto)

}