package com.shinjh1253.presentation.ui.search

import android.view.ViewTreeObserver
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.core.ui.BasicImage
import com.shinjh1253.presentation.core.ui.ErrorScreen
import com.shinjh1253.presentation.core.ui.LoadingScreen
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.model.SearchUiState

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
                ErrorScreen(message = stringResource(id = R.string.empty_content_list_message))
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

@Composable
fun VerticalGridContent(
    pagingItems: LazyPagingItems<DocumentUiState>,
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
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.imageUrl }
        ) { index ->
            pagingItems[index]?.let { documentUiState ->
                ImageItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    documentUiState = documentUiState,
                )
            }
        }
    }
}

@Composable
fun ImageItem(
    documentUiState: DocumentUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BasicImage(
                imageUrl = documentUiState.thumbnailUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Icon(
                painter = painterResource(
                    id =
                    if (documentUiState.bookmark) R.drawable.ic_bookmark_on else R.drawable.ic_bookmark_off
                ),
                contentDescription = stringResource(id = R.string.desc_bookmark),
                modifier = Modifier
                    .padding(all = 4.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        documentUiState.onBookmarkClick?.invoke(
                            documentUiState,
                            !documentUiState.bookmark
                        )
                    }
            )
        }

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            text = documentUiState.displaySitename,
            textAlign = TextAlign.Left,
            maxLines = 1,
            fontSize = 12.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
        )
    }
}