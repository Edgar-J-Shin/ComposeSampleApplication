package com.shinjh1253.presentation.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.model.SearchUiState
import com.shinjh1253.presentation.model.SearchUiStateProvider
import com.shinjh1253.presentation.ui.component.ErrorScreen
import com.shinjh1253.presentation.ui.component.LoadingScreen
import com.shinjh1253.presentation.ui.component.TextScreen
import com.shinjh1253.presentation.ui.component.item.ContentItem
import com.shinjh1253.presentation.ui.component.searchbar.SearchTopBar
import com.shinjh1253.presentation.ui.component.searchbar.SearchbarUiEvent
import com.shinjh1253.presentation.ui.theme.ComposeApplicationTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel.uiEffect) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SearchUiEffect.ShowSnackbar -> {
                    showSnackBar(
                        effect.state.getMessage(context),
                        effect.state.duration
                    )
                }
            }
        }
    }

    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    val searchResultUiState = viewModel.searchResultUiState.collectAsLazyPagingItems()

    SearchScreen(
        searchUiState = searchUiState,
        pagingItems = searchResultUiState,
        onSearchbarUiEvent = viewModel::dispatchSearchEvent,
        onBookmarkClick = viewModel::updateBookmark,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun SearchScreen(
    searchUiState: SearchUiState,
    pagingItems: LazyPagingItems<DocumentUiState>,
    modifier: Modifier = Modifier,
    onSearchbarUiEvent: (SearchbarUiEvent) -> Unit = { _ -> },
    onBookmarkClick: ((DocumentUiState, Boolean) -> Unit) = { _, _ -> }
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        SearchTopBar(
            searchUiState = searchUiState,
            onSearchbarUiEvent = onSearchbarUiEvent,
            modifier = Modifier
                .fillMaxWidth()
        )

        SearchResult(
            isQueryEmpty = { !searchUiState.queryNotEmpty() },
            pagingItems = pagingItems,
            onBookmarkClick = onBookmarkClick,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun SearchResult(
    isQueryEmpty: () -> Boolean,
    pagingItems: LazyPagingItems<DocumentUiState>,
    modifier: Modifier = Modifier,
    onBookmarkClick: ((DocumentUiState, Boolean) -> Unit) = { _, _ -> },
) {
    Box(
        modifier = modifier
    ) {
        val isLoading = pagingItems.loadState.refresh is LoadState.Loading
        val isNotLoading = pagingItems.loadState.refresh is LoadState.NotLoading
        val isError = pagingItems.loadState.refresh is LoadState.Error
        val isEmpty = pagingItems.itemCount == 0

        if (isQueryEmpty()) {
            TextScreen(message = stringResource(id = R.string.input_query_message))
        } else {
            when {
                isLoading -> {
                    LoadingScreen()
                }

                isError -> {
                    ErrorScreen(
                        message = stringResource(id = R.string.api_response_error_message),
                        primaryButton = {
                            Button(
                                onClick = pagingItems::retry
                            ) {
                                Text(text = stringResource(id = R.string.retry))
                            }
                        },
                    )
                }

                isEmpty -> {
                    ErrorScreen(message = stringResource(id = R.string.empty_search_result_message))
                }

                isNotLoading -> {
                    SearchResultContents(
                        pagingItems = pagingItems,
                        onBookmarkClick = onBookmarkClick,
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                }
            }
        }
    }
}

@Composable
fun SearchResultContents(
    pagingItems: LazyPagingItems<DocumentUiState>,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    onBookmarkClick: ((DocumentUiState, Boolean) -> Unit) = { _, _ -> }
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        state = gridState,

        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.list_margin_horizontal),
            vertical = dimensionResource(id = R.dimen.list_margin_vertical)
        ),
        modifier = modifier
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.imageUrl }
        ) { index ->
            pagingItems[index]?.let { documentUiState ->
                ContentItem(
                    documentUiState = documentUiState,
                    onBookmarkClick = onBookmarkClick,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Preview(showBackground = true, device = "spec:width=673.5dp,height=841dp,dpi=480")
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=480")
@Preview(showBackground = true, device = "spec:width=1920dp,height=1080dp,dpi=480")
@Composable
fun SearchScreenPreview(
    @PreviewParameter(SearchUiStateProvider::class) items: Pair<SearchUiState, PagingData<DocumentUiState>>,
) {
    val searchUiState = items.first
    val pagingItems = flowOf(items.second).collectAsLazyPagingItems()

    ComposeApplicationTheme {
        SearchScreen(
            searchUiState = searchUiState,
            pagingItems = pagingItems,
            onSearchbarUiEvent = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

