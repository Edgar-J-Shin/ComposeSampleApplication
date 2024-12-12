package com.shinjh1253.data.local.datasource

import kotlinx.coroutines.flow.Flow

interface KeywordLocalDataSource {

    fun getKeyword(): Flow<String>

    suspend fun setKeyword(keyword: String)
}