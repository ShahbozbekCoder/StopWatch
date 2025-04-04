package com.shahbozbek.stopwatch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahbozbek.stopwatch.data.models.newsdata.FavouriteArticle

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteArticle(article: FavouriteArticle)

    @Query("SELECT * FROM favourite_table")
    fun getFavouriteArticles(): List<FavouriteArticle>

    @Delete
    suspend fun deleteFavouriteArticles(article: FavouriteArticle)

}