package com.shinjh1253.presentation.core.state

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Immutable
import com.shinjh1253.presentation.R

@Immutable
sealed class SnackbarState(
    @StringRes val messageResId: Int = -1,
    val message: String = "",
    val duration: SnackbarDuration = SnackbarDuration.Short,
) {
    data class ErrorMessage(val errorMsg: String) :
        SnackbarState(message = errorMsg)

    data object EmptyQueryErrorMessage :
        SnackbarState(messageResId = R.string.search_query_empty_error)

    fun getMessage(context: Context): String = if (messageResId != -1) {
        context.getString(messageResId)
    } else {
        message
    }
}