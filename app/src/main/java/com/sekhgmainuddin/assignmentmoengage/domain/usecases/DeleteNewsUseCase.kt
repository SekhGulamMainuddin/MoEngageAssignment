package com.sekhgmainuddin.assignmentmoengage.domain.usecases

import com.sekhgmainuddin.assignmentmoengage.data.dto.Article
import com.sekhgmainuddin.assignmentmoengage.domain.repository.MainRepository
import javax.inject.Inject

class DeleteNewsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(article: Article) {
        repository.deleteSavedNews(article.url!!)
    }
}