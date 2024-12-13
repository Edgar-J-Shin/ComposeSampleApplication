package com.shinjh1253.domain.usecase

import com.shinjh1253.domain.repository.KeywordRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UpdateKeywordUseCase
@Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    suspend operator fun invoke(keyword: String) {
        keywordRepository.setKeyword(keyword)
    }
}
