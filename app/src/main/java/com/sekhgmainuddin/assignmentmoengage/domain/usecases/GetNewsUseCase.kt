package com.sekhgmainuddin.assignmentmoengage.domain.usecases

import android.util.Log
import com.sekhgmainuddin.assignmentmoengage.data.dto.NewsDto
import com.sekhgmainuddin.assignmentmoengage.domain.repository.MainRepository
import com.sekhgmainuddin.assignmentmoengage.helper.NetworkResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke() = flow<NetworkResult<NewsDto>> {
        try {
            emit(NetworkResult.Loading())
            val response = repository.getNews()
            val savedNews = repository.getAllSavedNews().first().map {
                it.url
            }.toList()
            response?.articles?.forEach {
                if (savedNews.contains(it.url)){
                    it.isBookmarked = true
                }
            }
            emit(NetworkResult.Success(response!!))
        } catch (e: Exception) {
            Log.d("DataReceived", "invoke: ${e.message}")
            emit(NetworkResult.Error("Some Error Occurred"))
        }
    }
}
