package com.shinjh1253.data.local.datasource

import com.shinjh1253.data.local.dao.BookmarkDao
import com.shinjh1253.data.local.model.LocalDocument
import com.shinjh1253.data.local.model.mapper.toDto
import com.shinjh1253.data.local.model.mapper.toLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkLocalDataSourceImpl
@Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkLocalDataSource {
    override fun getBookmarks(keyword: String): Flow<List<LocalDocument>> =
        if (keyword.isEmpty()) {
            bookmarkDao.selectAll()
                .map { documents ->
                    documents.distinctBy { document -> document.documentMetaEntity.imageUrl }
                }
        } else {
            bookmarkDao.selectByKeyword(keyword)
        }
            .map { documents ->
                documents.map { document -> document.toLocal() }
            }


    override suspend fun addBookmark(keyword: String, document: LocalDocument): Long =
        bookmarkDao.insert(document.toDto(keyword))

    override suspend fun removeBookmark(keyword: String, document: LocalDocument) =
        bookmarkDao.deleteByKeywordAndImageUrl(keyword, document.imageUrl)

    override suspend fun removeBookmarks(documents: List<LocalDocument>) {
        for (document in documents) {
            bookmarkDao.deleteByImageUrl(document.imageUrl)
        }
    }
}
