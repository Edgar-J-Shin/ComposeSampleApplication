package com.shinjh1253.domain.repository

import com.shinjh1253.domain.model.Document
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarks(keyword: String): Flow<List<Document>>

    suspend fun addBookmark(keyword: String, document: Document): Long

    suspend fun removeBookmark(keyword: String, document: Document): Unit

    fun removeBookmarks(bookmarks: List<Document>): Flow<Unit>
}
