package com.shinjh1253.presentation.ui.search

import android.view.ViewTreeObserver
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.core.ui.ErrorScreen
import com.shinjh1253.presentation.core.ui.LoadingScreen
import com.shinjh1253.presentation.core.ui.TextScreen
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.model.SearchUiState
import com.shinjh1253.presentation.model.SearchUiStateProvider
import com.shinjh1253.presentation.ui.component.VerticalGridContent
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
        onSearchUiEvent = viewModel::dispatchEvent,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun SearchScreen(
    searchUiState: SearchUiState,
    pagingItems: LazyPagingItems<DocumentUiState>,
    onSearchUiEvent: (SearchUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        SearchTopBar(
            searchUiState = searchUiState,
            onSearchUiEvent = onSearchUiEvent,
        )

        SearchResult(
            isQueryEmpty = { !searchUiState.queryNotEmpty() },
            pagingItems = pagingItems,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar(
    searchUiState: SearchUiState,
    onSearchUiEvent: (SearchUiEvent) -> Unit,
    searchActive: MutableState<Boolean> = remember { mutableStateOf(false) },
) {
    val focusManager = LocalFocusManager.current
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true

            if (!isKeyboardOpen) {
                focusManager.clearFocus()
            }
        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    SearchBar(
        query = searchUiState.query.keyword,
        onQueryChange = {
            onSearchUiEvent(SearchUiEvent.OnSearchTextChanged(it))
        },
        onSearch = {
            focusManager.clearFocus()
            onSearchUiEvent(SearchUiEvent.OnSearch(it))
        },
        active = false,
        onActiveChange = { },
        placeholder = {
            Text(text = stringResource(id = R.string.searchbar_hint))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.desc_search)
            )
        },
        trailingIcon = {
            if (searchActive.value) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.desc_clear),
                    modifier = Modifier.clickable {
                        if (searchUiState.queryNotEmpty()) {
                            onSearchUiEvent(SearchUiEvent.OnClearSearchTextClick)
                        } else {
                            searchActive.value = false
                        }
                    }
                )
            }
        },
        windowInsets = WindowInsets(
            top = 0.dp,
            bottom = 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusable()
    ) {

    }
}

@Composable
fun SearchResult(
    isQueryEmpty: () -> Boolean,
    pagingItems: LazyPagingItems<DocumentUiState>,
    modifier: Modifier = Modifier
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
                    VerticalGridContent(
                        pagingItems = pagingItems,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
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
            onSearchUiEvent = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

