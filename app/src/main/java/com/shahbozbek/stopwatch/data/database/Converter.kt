package com.shahbozbek.stopwatch.data.database

import androidx.room.TypeConverter
import com.shahbozbek.stopwatch.data.models.newsdata.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source{
        return Source(name, name)
    }

}