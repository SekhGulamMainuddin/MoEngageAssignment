package com.sekhgmainuddin.assignmentmoengage.data.repository

import com.sekhgmainuddin.assignmentmoengage.data.db.SavedNewsDao
import com.sekhgmainuddin.assignmentmoengage.data.db.entities.SavedNews
import com.sekhgmainuddin.assignmentmoengage.data.dto.NewsDto
import com.sekhgmainuddin.assignmentmoengage.data.remote.NetworkHelper
import com.sekhgmainuddin.assignmentmoengage.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import org.json.JSONObject
import javax.inject.Inject

class MainRepositoryImp @Inject constructor(private val dao: SavedNewsDao) : MainRepository{
    override suspend fun getNews(): NewsDto? {
        val response = NetworkHelper.requestGET("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
        return if (response==null){
            null
        }else {
            return Json.decodeFromString<NewsDto>(response)
        }

    }

    override suspend fun saveNews(savedNews: SavedNews) {
        dao.insert(savedNews)
    }

    override suspend fun deleteSavedNews(url: String) {
        dao.delete(url)
    }

    override suspend fun getAllSavedNews(): Flow<List<SavedNews>> {
       return dao.getAllSavedNews()
    }
}