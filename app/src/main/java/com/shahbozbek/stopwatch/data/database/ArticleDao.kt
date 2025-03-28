package com.shahbozbek.stopwatch.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahbozbek.stopwatch.data.models.newsdata.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAllArticle(article: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteArticle(article: Article)

    @Query("SELECT * FROM news_table")
    fun getFavouriteArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteFavouriteArticles(article: Article)

}