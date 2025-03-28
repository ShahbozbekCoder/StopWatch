package com.shahbozbek.stopwatch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shahbozbek.stopwatch.data.models.newsdata.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract val articleDao: ArticleDao
}