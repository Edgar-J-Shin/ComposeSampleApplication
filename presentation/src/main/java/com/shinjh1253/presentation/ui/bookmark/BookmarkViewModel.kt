package com.shinjh1253.presentation.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shinjh1253.domain.usecase.GetBookmarksUseCase
import com.shinjh1253.domain.usecase.GetKeywordUseCase
import com.shinjh1253.domain.usecase.UpdateBookmarkUseCase
import com.shinjh1253.presentation.core.state.SnackbarState
import com.shinjh1253.presentation.core.ui.EventDelegate
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.model.mapper.toEntity
import com.shinjh1253.presentation.model.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    getBookmarksUseCase: GetBookmarksUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
    getKeywordUseCase: GetKeywordUseCase,
) :
    ViewModel(),
    EventDelegate<BookmarkUiEffect, BookmarkUiEvent> by EventDelegate.EventDelegateImpl() {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                emitUiEffect(
                    BookmarkUiEffect.ShowSnackbar(
                        state = SnackbarState.ErrorMessage(
                            errorMsg = exception.message ?: "Unknown Error"
                        )
                    )
                )
            }
        }

    private val cachedKeyword = getKeywordUseCase()
        .stateIn(
            scope = viewModelScope,
            initialValue = "",
            started = SharingStarted.WhileSubscribed(5_000)
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val bookmarkUiState = getKeywordUseCase()
        .flatMapLatest { keyword ->
            getBookmarksUseCase(keyword)
                .map { documents ->
                    documents.map { document ->
                        document.toUiState().copy(
                            bookmark = true
                        ).apply {
                            onBookmarkClick = ::updateBookmark
                        }
                    }
                }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    private fun updateBookmark(
        documentUiState: DocumentUiState,
        isBookmarked: Boolean,
    ) {
        viewModelScope.launch(coroutineExceptionHandler) {
            updateBookmarkUseCase(
                keyword = cachedKeyword.value,
                document = documentUiState.toEntity(),
                isBookmarked = isBookmarked,
            )
        }
    }

    override fun dispatchEvent(event: BookmarkUiEvent) {

    }
}
