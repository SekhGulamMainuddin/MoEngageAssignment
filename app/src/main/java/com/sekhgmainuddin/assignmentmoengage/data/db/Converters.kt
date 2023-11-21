package com.sekhgmainuddin.assignmentmoengage.data.db

import androidx.room.TypeConverter
import com.sekhgmainuddin.assignmentmoengage.data.dto.Source
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun stringToSource(source: String): Source {
        return Json.decodeFromString<Source>(source)
    }

    @TypeConverter
    fun sourceToString(source: Source): String {
        return Json.encodeToString(source)
    }
}