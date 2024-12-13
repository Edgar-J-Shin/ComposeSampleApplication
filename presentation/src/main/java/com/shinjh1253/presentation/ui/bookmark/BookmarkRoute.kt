package com.shinjh1253.presentation.ui.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.core.ui.ErrorScreen
import com.shinjh1253.presentation.core.ui.LoadingScreen
import com.shinjh1253.presentation.core.ui.UiState
import com.shinjh1253.presentation.model.BookmarkUiStateProvider
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.ui.component.ContentItem
import com.shinjh1253.presentation.ui.theme.ComposeApplicationTheme

@Composable
fun BookmarkRoute(
    viewModel: BookmarkViewModel = hiltViewModel(),
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel.uiEffect) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is BookmarkUiEffect.ShowSnackbar -> {
                    showSnackBar(
                        effect.state.getMessage(context),
                        effect.state.duration
                    )
                }
            }
        }
    }

    val bookmarkUiState by viewModel.bookmarkUiState.collectAsStateWithLifecycle()

    BookmarkScreen(
        bookmarkUiState,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun BookmarkScreen(
    bookmarkUiState: UiState<List<DocumentUiState>>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        when (bookmarkUiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }

            is UiState.Error -> {
                ErrorScreen(
                    message = stringResource(id = R.string.api_response_error_message),
                )
            }

            is UiState.Success -> {
                if (bookmarkUiState.data.isEmpty()) {
                    ErrorScreen(message = stringResource(id = R.string.empty_content_list_message))
                } else {
                    VerticalGridBookmarkContent(
                        bookmarkUiState = bookmarkUiState.data,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun VerticalGridBookmarkContent(
    bookmarkUiState: List<DocumentUiState>,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,

        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.list_margin_horizontal),
            vertical = dimensionResource(id = R.dimen.list_margin_vertical)
        ),
        modifier = modifier
    ) {
        items(
            items = bookmarkUiState,
            key = { it.imageUrl }
        ) { document ->
            ContentItem(
                modifier = Modifier
                    .fillMaxWidth(),
                documentUiState = document,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenPreview(
    @PreviewParameter(BookmarkUiStateProvider::class) item: UiState<List<DocumentUiState>>,
) {
    ComposeApplicationTheme {
        BookmarkScreen(
            bookmarkUiState = item,
            modifier = Modifier.fillMaxSize()
        )
    }
}