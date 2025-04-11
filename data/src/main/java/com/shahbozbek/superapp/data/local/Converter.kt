package com.shahbozbek.superapp.data.local

import androidx.room.TypeConverter
import com.shahbozbek.superapp.data.dto.newsDataDto.SourceDto

class Converters {

    @TypeConverter
    fun fromSource(sourceDto: SourceDto): String? {
        return sourceDto.name
    }

    @TypeConverter
    fun toSource(name: String): SourceDto {
        return SourceDto(name, name)
    }
}