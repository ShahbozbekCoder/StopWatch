package com.shahbozbek.superApp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahbozbek.superApp.data.dto.newsDataDto.ArticleDto

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsData(newsData: List<ArticleDto>)

    @Query("SELECT * FROM article_table")
    suspend fun getNewsData(): List<ArticleDto>

    @Query("DELETE FROM article_table")
    suspend fun deleteAllNewsData()

}