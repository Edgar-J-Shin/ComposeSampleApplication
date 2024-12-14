package com.shinjh1253.presentation.ui.component.searchbar

import android.view.ViewTreeObserver
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.model.SearchUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchUiState: SearchUiState,
    onSearchbarUiEvent: (SearchbarUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    var active by remember { mutableStateOf(false) }

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
            onSearchbarUiEvent(SearchbarUiEvent.OnSearchTextChanged(it))
        },
        onSearch = {
            focusManager.clearFocus()
            onSearchbarUiEvent(SearchbarUiEvent.OnSearch(it))
        },
        active = false,
        onActiveChange = { active = it },
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
            if (active) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.desc_clear),
                    modifier = Modifier.clickable {
                        if (searchUiState.queryNotEmpty()) {
                            onSearchbarUiEvent(SearchbarUiEvent.OnClearSearchTextClick)
                        }
                    }
                )
            }
        },
        windowInsets = WindowInsets(
            top = 0.dp,
            bottom = 0.dp,
        ),
        modifier = modifier
            .focusable()
    ) {

    }
}