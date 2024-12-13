package com.shinjh1253.presentation.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shinjh1253.domain.usecase.GetBookmarksUseCase
import com.shinjh1253.domain.usecase.RemoveBookmarksUseCase
import com.shinjh1253.domain.usecase.UpdateBookmarkUseCase
import com.shinjh1253.presentation.core.state.SnackbarState
import com.shinjh1253.presentation.core.ui.EventDelegate
import com.shinjh1253.presentation.core.ui.UiState
import com.shinjh1253.presentation.core.ui.asUiState
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.model.KeywordUiState
import com.shinjh1253.presentation.model.SearchUiState
import com.shinjh1253.presentation.model.mapper.toEntity
import com.shinjh1253.presentation.model.mapper.toUiState
import com.shinjh1253.presentation.ui.component.searchbar.SearchbarUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    getBookmarksUseCase: GetBookmarksUseCase,
    private val removeBookmarksUseCase: RemoveBookmarksUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
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

    private val _selectedBookmarks: MutableStateFlow<List<DocumentUiState>> =
        MutableStateFlow(emptyList())
    val selectedBookmarks = _selectedBookmarks.asStateFlow()

    private val _totalCount = MutableStateFlow(0)
    val totalCount = _totalCount.asStateFlow()

    private val _editModeUiState = MutableStateFlow(false)
    val editModeUiState = _editModeUiState.asStateFlow()

    private val _searchUiState = MutableStateFlow(SearchUiState.Empty)
    val searchUiState = _searchUiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val bookmarkUiState = searchUiState
        .debounce(1000L)
        .map { it.query.keyword }
        .flatMapLatest { keyword ->
            getBookmarksUseCase(keyword).combine(selectedBookmarks) { documents, selectedBookmarks ->
                _totalCount.emit(documents.size)

                documents.map { document ->
                    document.toUiState().copy(
                        bookmark = true,
                        isSelected = selectedBookmarks.find { it.imageUrl == document.imageUrl } != null
                    )
                }
            }
        }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            initialValue = UiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    private fun clearSelectedBookmarks() {
        viewModelScope.launch {
            _selectedBookmarks.emit(emptyList())
        }
    }

    private fun updateBookmark(
        documentUiState: DocumentUiState,
        isBookmarked: Boolean,
    ) {
        viewModelScope.launch(coroutineExceptionHandler) {
            updateBookmarkUseCase(
                keyword = searchUiState.value.query.keyword,
                document = documentUiState.toEntity(),
                isBookmarked = isBookmarked,
            )
        }
    }

    private fun updateSearchText(newText: String) {
        _searchUiState.update { it.copy(query = KeywordUiState(newText)) }
    }

    private fun clearSearchText() {
        _searchUiState.update { it.copy(query = KeywordUiState("")) }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                emitUiEffect(BookmarkUiEffect.ShowSnackbar(state = SnackbarState.EmptyQueryErrorMessage))
                clearSearchText()
            } else {
                updateSearchText(query)
            }
        }
    }

    fun dispatchSearchEvent(event: SearchbarUiEvent) {
        when (event) {
            is SearchbarUiEvent.OnSearchTextChanged -> {
                updateSearchText(event.query)
            }

            is SearchbarUiEvent.OnClearSearchTextClick -> {
                clearSearchText()
            }

            is SearchbarUiEvent.OnSearch -> {
                search(event.keyword)
            }
        }
    }

    fun changeEditMode(editMode: Boolean) {
        viewModelScope.launch {
            _editModeUiState.emit(editMode)
        }
    }

    fun removeBookmarks() {
        viewModelScope.launch {
            removeBookmarksUseCase(
                bookmarks = selectedBookmarks.value.map { it.toEntity() })
                .collect {
                    clearSelectedBookmarks()
                    changeEditMode(false)
                }
        }
    }

    fun changeBookmarkChecked(documentUiState: DocumentUiState, check: Boolean) {
        viewModelScope.launch {
            if (check) {
                _selectedBookmarks.emit(_selectedBookmarks.value + documentUiState)
            } else {
                _selectedBookmarks.emit(_selectedBookmarks.value.filter { it.imageUrl != documentUiState.imageUrl })

            }
        }
    }
}
