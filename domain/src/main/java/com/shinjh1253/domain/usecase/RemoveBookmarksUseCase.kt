package com.shinjh1253.domain.usecase

import com.shinjh1253.domain.model.Document
import com.shinjh1253.domain.repository.BookmarkRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class RemoveBookmarksUseCase
@Inject
constructor(
    private val bookmarkRepository: BookmarkRepository,
) {
    operator fun invoke(bookmarks: List<Document>): Flow<Unit> =
        bookmarkRepository.removeBookmarks(bookmarks)
}
