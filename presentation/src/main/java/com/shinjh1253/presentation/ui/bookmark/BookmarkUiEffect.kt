package com.shinjh1253.presentation.ui.bookmark

import com.shinjh1253.presentation.core.state.SnackbarState

sealed interface BookmarkUiEffect {
    data class ShowSnackbar(
        val state: SnackbarState,
    ) : BookmarkUiEffect
}
