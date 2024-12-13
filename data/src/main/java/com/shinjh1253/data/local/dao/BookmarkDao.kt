package com.shinjh1253.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.shinjh1253.data.local.model.DocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao : BaseDao<DocumentEntity> {

    @Query("SELECT * FROM documents")
    fun selectAll(): Flow<List<DocumentEntity>>

    @Query("SELECT * FROM documents WHERE :keyword == keyword")
    fun selectByKeyword(keyword: String): Flow<List<DocumentEntity>>

    @Query("DELETE FROM documents WHERE :imageUrl == imageUrl")
    suspend fun deleteByImageUrl(imageUrl: String)

    @Query("DELETE FROM documents WHERE :keyword == keyword AND :imageUrl == imageUrl")
    suspend fun deleteByKeywordAndImageUrl(keyword: String, imageUrl: String)

    @Delete
    fun deleteDocuments(documents: List<DocumentEntity>)
}