package com.shinjh1253.presentation.ui.main

sealed interface MainUiEffect {
    data class ErrorMessage(
        val message: String,
    ) : MainUiEffect
}
