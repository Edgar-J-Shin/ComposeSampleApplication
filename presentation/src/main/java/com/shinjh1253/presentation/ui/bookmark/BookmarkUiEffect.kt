package com.shinjh1253.presentation.ui.bookmark

sealed interface BookmarkUiEffect {
    data class ErrorMessage(
        val message: String,
    ) : BookmarkUiEffect
}
