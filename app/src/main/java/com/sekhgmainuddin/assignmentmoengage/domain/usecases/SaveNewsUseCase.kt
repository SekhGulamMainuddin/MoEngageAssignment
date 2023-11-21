package com.sekhgmainuddin.assignmentmoengage.domain.usecases

import com.sekhgmainuddin.assignmentmoengage.data.dto.Article
import com.sekhgmainuddin.assignmentmoengage.data.dto.toSavedNews
import com.sekhgmainuddin.assignmentmoengage.domain.repository.MainRepository
import javax.inject.Inject

class SaveNewsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(article: Article) {
            repository.saveNews(article.toSavedNews())
    }
}