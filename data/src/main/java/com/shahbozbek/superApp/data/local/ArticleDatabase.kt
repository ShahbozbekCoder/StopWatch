package com.shahbozbek.superApp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shahbozbek.superApp.data.dto.newsDataDto.ArticleDto
import com.shahbozbek.superApp.data.dto.newsDataDto.FavouriteArticleDto
import com.shahbozbek.superApp.data.local.dao.ArticleDao
import com.shahbozbek.superApp.data.local.dao.FavouritesDao

@Database(
    entities = [ArticleDto::class, FavouriteArticleDto::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun favouritesDao(): FavouritesDao

    companion object {
        const val DATABASE_NAME = "article_database"
    }

}