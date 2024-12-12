package com.shinjh1253.presentation.ui.bookmark

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BookmarkRoute(
    viewModel: BookmarkViewModel = hiltViewModel(),
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {
    BookmarkScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun BookmarkScreen(
    modifier: Modifier = Modifier,
) {

}