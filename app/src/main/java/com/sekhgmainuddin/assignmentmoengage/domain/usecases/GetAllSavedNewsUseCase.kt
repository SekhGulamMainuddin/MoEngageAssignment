package com.sekhgmainuddin.assignmentmoengage.domain.usecases

import com.sekhgmainuddin.assignmentmoengage.domain.repository.MainRepository
import javax.inject.Inject

class GetAllSavedNewsUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke() = repository.getAllSavedNews()
}