package com.shinjh1253.data.local.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class KeywordLocalDataSourceImpl
@Inject
constructor() : KeywordLocalDataSource {
    private val _keywordFlow = MutableStateFlow("")
    val keywordFlow: Flow<String> get() = _keywordFlow.asStateFlow()

    override fun getKeyword(): Flow<String> {
        return keywordFlow
    }

    override suspend fun setKeyword(keyword: String) {
        _keywordFlow.value = keyword
    }
}