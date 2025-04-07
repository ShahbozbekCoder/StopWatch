package com.shahbozbek.superApp.data.local

import androidx.room.TypeConverter
import com.shahbozbek.superApp.data.models.newsDataDto.SourceDto

class Converters {

    @TypeConverter
    fun fromSource(sourceDto: SourceDto): String? {
        return sourceDto.name
    }

    @TypeConverter
    fun toSource(name: String): SourceDto{
        return SourceDto(name, name)
    }

}