package com.shinjh1253.data.local.datasource

import com.shinjh1253.data.local.model.LocalDocument
import kotlinx.coroutines.flow.Flow

interface BookmarkLocalDataSource {
    fun getBookmarks(keyword: String): Flow<List<LocalDocument>>

    suspend fun addBookmark(keyword: String, document: LocalDocument): Long

    suspend fun removeBookmark(keyword: String, document: LocalDocument): Unit
}
