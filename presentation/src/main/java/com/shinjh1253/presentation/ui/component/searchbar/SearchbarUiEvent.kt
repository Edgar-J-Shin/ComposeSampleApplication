package com.shinjh1253.presentation.ui.component.searchbar

sealed interface SearchbarUiEvent {
    data class OnSearchTextChanged(val query: String) : SearchbarUiEvent

    data object OnClearSearchTextClick : SearchbarUiEvent

    data class OnSearch(val keyword: String) : SearchbarUiEvent
}
