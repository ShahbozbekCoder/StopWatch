package com.shahbozbek.superApp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shahbozbek.superApp.data.models.newsdata.Article
import com.shahbozbek.superApp.data.models.newsdata.FavouriteArticle

@Database(entities = [Article::class, FavouriteArticle::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun favouritesDao(): FavouritesDao

    companion object{
        const val DATABASE_NAME = "article_database"
    }

}