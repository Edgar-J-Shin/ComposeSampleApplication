package com.shinjh1253.domain.usecase

import com.shinjh1253.domain.repository.KeywordRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetKeywordUseCase
@Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    operator fun invoke(): Flow<String> {
        return keywordRepository.getKeyword()
    }
}
