package com.shinjh1253.presentation.ui.search

sealed interface SearchUiEvent {
    data class OnSearchTextChanged(val query: String) : SearchUiEvent

    data object OnClearSearchTextClick : SearchUiEvent

    data class OnSearch(val keyword: String) : SearchUiEvent
}
