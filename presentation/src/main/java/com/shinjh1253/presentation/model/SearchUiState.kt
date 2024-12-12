package com.shinjh1253.presentation.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Stable
data class SearchUiState(
    val query: KeywordUiState,
) {
    fun queryNotEmpty() = query.keyword.isNotEmpty()

    companion object {
        val Empty = SearchUiState(
            query = KeywordUiState(""),
        )
    }
}

class SearchUiStateProvider : PreviewParameterProvider<Pair<SearchUiState, Boolean>> {

    override val values: Sequence<Pair<SearchUiState, Boolean>>
        get() = sequenceOf(
            SearchUiState(
                query = KeywordUiState(""),
            ) to false,
            SearchUiState(
                query = KeywordUiState("test"),
            ) to true,
        )
}
