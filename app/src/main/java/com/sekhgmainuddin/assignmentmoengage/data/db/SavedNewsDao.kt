package com.sekhgmainuddin.assignmentmoengage.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sekhgmainuddin.assignmentmoengage.data.db.entities.SavedNews
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedNews: SavedNews)

    @Query("delete from saved_news where url=:url")
    suspend fun delete(url: String)

    @Query("select * from saved_news")
    fun getAllSavedNews(): Flow<List<SavedNews>>

}