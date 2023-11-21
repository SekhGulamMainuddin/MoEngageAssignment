package com.sekhgmainuddin.assignmentmoengage.presentation.main.uiStates

import com.sekhgmainuddin.assignmentmoengage.data.dto.NewsDto

sealed class GetNewsState {
    object Initial : GetNewsState()
    object Loading : GetNewsState()
    data class Loaded(val newsDto: NewsDto) : GetNewsState()
    data class Error(val message: String) : GetNewsState()
}
