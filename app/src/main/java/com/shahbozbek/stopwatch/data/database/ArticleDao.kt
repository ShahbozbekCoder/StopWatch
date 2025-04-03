package com.shahbozbek.stopwatch.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shahbozbek.stopwatch.data.models.newsdata.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsData(newsData: List<Article>)

    @Query("SELECT * FROM article_table")
    suspend fun getNewsData(): List<Article>

    @Query("DELETE FROM article_table")
    suspend fun deleteAllNewsData()

}