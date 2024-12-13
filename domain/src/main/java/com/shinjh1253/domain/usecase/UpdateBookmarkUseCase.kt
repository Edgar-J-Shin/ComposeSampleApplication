package com.shinjh1253.domain.usecase

import com.shinjh1253.domain.model.Document
import com.shinjh1253.domain.repository.BookmarkRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UpdateBookmarkUseCase
@Inject
constructor(
    private val bookmarkRepository: BookmarkRepository,
) {
    suspend operator fun invoke(
        keyword: String = "",
        document: Document,
        isBookmarked: Boolean,
    ) {
        if (isBookmarked) {
            bookmarkRepository.addBookmark(keyword, document)
        } else {
            bookmarkRepository.removeBookmark(keyword, document)
        }
    }
}
