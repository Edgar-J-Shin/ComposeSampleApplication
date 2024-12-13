package com.shinjh1253.presentation.ui.component.item

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.model.DocumentUiState

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
                ContentItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    documentUiState = documentUiState,
                )
            }
        }
    }
}