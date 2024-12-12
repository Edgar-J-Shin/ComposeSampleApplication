package com.shinjh1253.presentation.ui.search

import com.shinjh1253.presentation.core.state.SnackbarState

sealed interface SearchUiEffect {
    data class ErrorMessage(
        val message: String,
    ) : SearchUiEffect

    data class ShowSnackbar(
        val state: SnackbarState,
    ) : SearchUiEffect
}
