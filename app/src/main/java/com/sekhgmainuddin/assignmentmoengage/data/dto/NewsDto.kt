package com.sekhgmainuddin.assignmentmoengage.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val articles: MutableList<Article>,
    val status: String
)