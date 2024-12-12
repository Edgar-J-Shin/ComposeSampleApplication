package com.shinjh1253.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.shinjh1253.domain.usecase.GetImagesUseCase
import com.shinjh1253.presentation.core.state.SnackbarState
import com.shinjh1253.presentation.core.ui.EventDelegate
import com.shinjh1253.presentation.model.KeywordUiState
import com.shinjh1253.presentation.model.SearchUiState
import com.shinjh1253.presentation.model.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) :
    ViewModel(),
    EventDelegate<SearchUiEffect, SearchUiEvent> by EventDelegate.EventDelegateImpl() {

    private val _searchUiState = MutableStateFlow(SearchUiState.Empty)
    val searchUiState = _searchUiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResultUiState = searchUiState
        .debounce(1000L)
        .flatMapLatest {
            if (it.queryNotEmpty()) {
                getImagesUseCase(it.query.keyword)
                    .map { pagingData -> pagingData.map { document -> document.toUiState() } }
                    .cachedIn(viewModelScope)
            } else {
                flowOf(PagingData.empty())
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = PagingData.empty(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    private fun changeSearchText(newText: String) {
        _searchUiState.update { it.copy(query = KeywordUiState(newText)) }
    }

    private fun clearSearchText() {
        _searchUiState.update { it.copy(query = KeywordUiState("")) }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                emitUiEffect(SearchUiEffect.ShowSnackbar(state = SnackbarState.SearchQueryEmptyError))
            }
        }
    }

    override fun dispatchEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchTextChanged -> {
                changeSearchText(event.query)
            }

            is SearchUiEvent.OnClearSearchTextClick -> {
                clearSearchText()
            }

            is SearchUiEvent.OnSearch -> {
                search(event.keyword)
            }
        }
    }
}
