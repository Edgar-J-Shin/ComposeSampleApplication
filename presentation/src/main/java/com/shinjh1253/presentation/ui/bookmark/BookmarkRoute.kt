package com.shinjh1253.presentation.ui.bookmark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.core.ui.UiState
import com.shinjh1253.presentation.model.BookmarkUiStateProvider
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.model.SearchUiState
import com.shinjh1253.presentation.ui.component.ErrorScreen
import com.shinjh1253.presentation.ui.component.LoadingScreen
import com.shinjh1253.presentation.ui.component.item.BookmarkItem
import com.shinjh1253.presentation.ui.component.searchbar.SearchTopBar
import com.shinjh1253.presentation.ui.component.searchbar.SearchbarUiEvent
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

    val selectedBookmarks by viewModel.selectedBookmarks.collectAsStateWithLifecycle()
    val totalCount by viewModel.totalCount.collectAsStateWithLifecycle()
    val editModeUiState by viewModel.editModeUiState.collectAsStateWithLifecycle()
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    val bookmarkUiState by viewModel.bookmarkUiState.collectAsStateWithLifecycle()

    BookmarkScreen(
        editModeUiState = editModeUiState,
        searchUiState = searchUiState,
        bookmarkUiState = bookmarkUiState,
        selectedCount = { selectedBookmarks.count() },
        totalCount = { totalCount },
        onSearchbarUiEvent = viewModel::dispatchSearchEvent,
        onEditModeClick = viewModel::changeEditMode,
        onRemoveBookmarksClick = viewModel::removeBookmarks,
        onCheckedChange = viewModel::changeBookmarkChecked,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun BookmarkScreen(
    editModeUiState: Boolean,
    searchUiState: SearchUiState,
    bookmarkUiState: UiState<List<DocumentUiState>>,
    modifier: Modifier = Modifier,
    selectedCount: () -> Int = { 0 },
    totalCount: () -> Int = { 0 },
    onSearchbarUiEvent: (SearchbarUiEvent) -> Unit = {},
    onEditModeClick: (Boolean) -> Unit = {},
    onRemoveBookmarksClick: () -> Unit = {},
    onCheckedChange: (DocumentUiState, Boolean) -> Unit = { _, _ -> },
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        if (editModeUiState) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    text = stringResource(id = R.string.selected_count, selectedCount()),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = 12.sp,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    painter = painterResource(
                        id =
                        if (selectedCount() > 0) R.drawable.delete_on else R.drawable.delete_off
                    ),
                    contentDescription = stringResource(id = R.string.desc_close),
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .clickable {
                            if (selectedCount() > 0) {
                                onRemoveBookmarksClick()
                            }
                        }
                )

                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = stringResource(id = R.string.desc_close),
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .clickable {
                            onEditModeClick(false)
                        }
                )
            }
        } else {
            SearchTopBar(
                searchUiState = searchUiState,
                onSearchbarUiEvent = onSearchbarUiEvent,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    text = stringResource(id = R.string.total_count, totalCount()),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = 12.sp,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.mode_edit),
                    contentDescription = stringResource(id = R.string.desc_edit_mode),
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .clickable {
                            onEditModeClick(true)
                        }
                )
            }
        }

        BookmarkSearchResult(
            editModeUiState = editModeUiState,
            bookmarkUiState = bookmarkUiState,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun BookmarkSearchResult(
    editModeUiState: Boolean,
    bookmarkUiState: UiState<List<DocumentUiState>>,
    modifier: Modifier = Modifier,
    onCheckedChange: (DocumentUiState, Boolean) -> Unit = { _, _ -> },
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
                    ErrorScreen(message = stringResource(id = R.string.empty_bookmarks_message))
                } else {
                    BookmarkContents(
                        editModeUiState = editModeUiState,
                        bookmarkUiState = bookmarkUiState.data,
                        onCheckedChange = onCheckedChange,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarkContents(
    editModeUiState: Boolean,
    bookmarkUiState: List<DocumentUiState>,
    modifier: Modifier = Modifier,
    onCheckedChange: (DocumentUiState, Boolean) -> Unit = { _, _ -> },
) {
    LazyColumn(
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
            BookmarkItem(
                editModeUiState = editModeUiState,
                documentUiState = document,
                onCheckedChange = onCheckedChange,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenPreview(
    @PreviewParameter(BookmarkUiStateProvider::class) items: Pair<SearchUiState, UiState<List<DocumentUiState>>>,
) {
    val searchUiState = items.first
    val bookmarkUiState = items.second

    ComposeApplicationTheme {
        BookmarkScreen(
            searchUiState = searchUiState,
            bookmarkUiState = bookmarkUiState,
            onSearchbarUiEvent = { },
            modifier = Modifier.fillMaxSize(),
            editModeUiState = false
        )
    }
}