package com.sekhgmainuddin.assignmentmoengage.data.dto

import com.sekhgmainuddin.assignmentmoengage.data.db.entities.SavedNews
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    var isBookmarked: Boolean = false
)

fun Article.toSavedNews() = SavedNews(
    author,
    content,
    description,
    publishedAt,
    source,
    title,
    url!!,
    urlToImage,
)