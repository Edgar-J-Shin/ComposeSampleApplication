package com.shinjh1253.presentation.core.state

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Immutable
import com.shinjh1253.presentation.R

@Immutable
sealed class SnackbarState(
    @StringRes val messageResId: Int,
    val duration: SnackbarDuration = SnackbarDuration.Short,
) {
    data object SearchQueryEmptyError :
        SnackbarState(messageResId = R.string.search_query_empty_error)
}