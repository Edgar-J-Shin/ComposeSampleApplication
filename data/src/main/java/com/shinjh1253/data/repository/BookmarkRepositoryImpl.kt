package com.shinjh1253.data.repository

import com.shinjh1253.data.di.IoDispatcher
import com.shinjh1253.data.local.datasource.BookmarkLocalDataSource
import com.shinjh1253.data.local.model.mapper.toEntity
import com.shinjh1253.data.local.model.mapper.toLocal
import com.shinjh1253.domain.model.Document
import com.shinjh1253.domain.repository.BookmarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl
@Inject
constructor(
    private val bookmarkLocalDataSource: BookmarkLocalDataSource,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
) : BookmarkRepository {
    override fun getBookmarks(keyword: String): Flow<List<Document>> =
        bookmarkLocalDataSource
            .getBookmarks(keyword)
            .map {
                it.map { document -> document.toEntity() }
            }
            .flowOn(ioDispatcher)

    override suspend fun addBookmark(keyword: String, document: Document) =
        bookmarkLocalDataSource
            .addBookmark(
                keyword = keyword,
                document = document.toLocal()
            )

    override suspend fun removeBookmark(keyword: String, document: Document) =
        bookmarkLocalDataSource
            .removeBookmark(
                keyword = keyword,
                document = document.toLocal()
            )

    override fun removeBookmarks(bookmarks: List<Document>) = flow {
        bookmarkLocalDataSource
            .removeBookmarks(bookmarks.map { it.toLocal() })

        emit(Unit)
    }.flowOn(ioDispatcher)
}
