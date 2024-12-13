package com.shinjh1253.data.repository

import com.shinjh1253.data.local.datasource.KeywordLocalDataSource
import com.shinjh1253.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val keywordLocalDataSource: KeywordLocalDataSource,
) : KeywordRepository {
    override fun getKeyword(): Flow<String> = keywordLocalDataSource.getKeyword()

    override suspend fun setKeyword(keyword: String) {
        keywordLocalDataSource.setKeyword(keyword)
    }
}