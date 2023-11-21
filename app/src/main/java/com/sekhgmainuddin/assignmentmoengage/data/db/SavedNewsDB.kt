package com.sekhgmainuddin.assignmentmoengage.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sekhgmainuddin.assignmentmoengage.data.db.entities.SavedNews

@TypeConverters(Converters::class)
@Database(entities = [SavedNews::class], version = 1)
abstract class SavedNewsDB : RoomDatabase() {
    abstract fun getDao(): SavedNewsDao
}