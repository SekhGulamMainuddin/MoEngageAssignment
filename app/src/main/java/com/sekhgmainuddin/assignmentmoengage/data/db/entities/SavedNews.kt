package com.sekhgmainuddin.assignmentmoengage.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.sekhgmainuddin.assignmentmoengage.data.db.Converters
import com.sekhgmainuddin.assignmentmoengage.data.dto.Article
import com.sekhgmainuddin.assignmentmoengage.data.dto.Source

// Added this to save the bookmarked news

@Entity(tableName = "saved_news")
data class SavedNews(
    @ColumnInfo("author") val author: String?,
    @ColumnInfo("content") val content: String?,
    @ColumnInfo("desc") val description: String?,
    @ColumnInfo("publishedAt") val publishedAt: String?,
    @ColumnInfo("source") @TypeConverters(Converters::class) val source: Source?,
    @ColumnInfo("title") val title: String?,
    @ColumnInfo("url") @PrimaryKey(autoGenerate = false) val url: String,
    @ColumnInfo("urlToImage") val urlToImage: String?,
)

fun SavedNews.toArticle(): Article {
    return Article(
        author,
        content,
        description,
        publishedAt,
        source,
        title,
        url,
        urlToImage,
        isBookmarked = true
    )
}