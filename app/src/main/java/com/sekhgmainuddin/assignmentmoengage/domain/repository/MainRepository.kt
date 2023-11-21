package com.sekhgmainuddin.assignmentmoengage.domain.repository

import com.sekhgmainuddin.assignmentmoengage.data.db.entities.SavedNews
import com.sekhgmainuddin.assignmentmoengage.data.dto.NewsDto
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface MainRepository {
    suspend fun getNews(): NewsDto?
    suspend fun saveNews(savedNews: SavedNews)
    suspend fun deleteSavedNews(url: String)
    suspend fun getAllSavedNews() : Flow<List<SavedNews>>
}