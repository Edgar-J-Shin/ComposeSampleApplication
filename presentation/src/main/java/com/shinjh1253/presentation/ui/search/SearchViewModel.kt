package com.shinjh1253.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.shinjh1253.domain.usecase.GetBookmarksUseCase
import com.shinjh1253.domain.usecase.GetImagesUseCase
import com.shinjh1253.domain.usecase.UpdateBookmarkUseCase
import com.shinjh1253.presentation.core.state.SnackbarState
import com.shinjh1253.presentation.core.ui.EventDelegate
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.model.KeywordUiState
import com.shinjh1253.presentation.model.SearchUiState
import com.shinjh1253.presentation.model.mapper.toEntity
import com.shinjh1253.presentation.model.mapper.toUiState
import com.shinjh1253.presentation.ui.component.searchbar.SearchbarEventDelegate
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
class SearchViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
) :
    ViewModel(),
    EventDelegate<SearchUiEffect, SearchUiEvent> by EventDelegate.EventDelegateImpl(),
    SearchbarEventDelegate<SearchbarUiEvent> by SearchbarEventDelegate.SearchbarEventDelegateImpl(){

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                emitUiEffect(
                    SearchUiEffect.ShowSnackbar(
                        state = SnackbarState.ErrorMessage(
                            errorMsg = exception.message ?: "Unknown Error"
                        )
                    )
                )
            }
        }

    private val _searchUiState = MutableStateFlow(SearchUiState.Empty)
    val searchUiState = _searchUiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResultUiState = searchUiState
        .debounce(1000L)
        .map { it.query.keyword }
        .flatMapLatest { keyword ->
            getImagesUseCase(keyword)
                .map { pagingData ->
                    pagingData.map { document ->
                        document.toUiState()
                    }
                }
                .cachedIn(viewModelScope)
                .combine(getBookmarksUseCase(keyword)) { pagingData, bookmarks ->
                    // 북마크 적용
                    pagingData.map { documentUiState ->
                        documentUiState.copy(
                            bookmark = bookmarks.any { bookmark ->
                                bookmark.imageUrl == documentUiState.imageUrl
                            }
                        )
                    }
                }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = PagingData.empty(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    private fun updateSearchText(newText: String) {
        _searchUiState.update { it.copy(query = KeywordUiState(newText)) }
    }

    private fun clearSearchText() {
        _searchUiState.update { it.copy(query = KeywordUiState("")) }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                emitUiEffect(SearchUiEffect.ShowSnackbar(state = SnackbarState.EmptyQueryErrorMessage))
                clearSearchText()
            } else {
                updateSearchText(query)
            }
        }
    }

    fun updateBookmark(
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

    override fun dispatchSearchbarEvent(event: SearchbarUiEvent) {
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
}
