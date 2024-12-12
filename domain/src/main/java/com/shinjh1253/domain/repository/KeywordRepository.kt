package com.shinjh1253.domain.repository

import kotlinx.coroutines.flow.Flow

interface KeywordRepository {
    fun getKeyword(): Flow<String>

    suspend fun setKeyword(keyword: String)
}
