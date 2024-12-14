package com.shinjh1253.presentation.ui.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.model.BookmarkItemUiStateProvider
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.ui.component.image.BasicImage
import com.shinjh1253.presentation.ui.theme.ComposeApplicationTheme

@Composable
fun BookmarkItem(
    editModeUiState: Boolean,
    documentUiState: DocumentUiState,
    modifier: Modifier = Modifier,
    onCheckedChange: (DocumentUiState, Boolean) -> Unit = { _, _ -> },
) {
    Column(
        modifier = modifier
            .padding(all = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
        ) {
            BasicImage(
                imageUrl = documentUiState.thumbnailUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .aspectRatio(0.9f)
                    .padding(all = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                if (documentUiState.displaySitename.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        text = stringResource(
                            id = R.string.site_name,
                            documentUiState.displaySitename
                        ),
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    text = stringResource(
                        id = R.string.datetime,
                        documentUiState.datetime.toLocalDate()
                    ),
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            if (editModeUiState) {
                Checkbox(
                    checked = documentUiState.isSelected,
                    onCheckedChange = {
                        onCheckedChange(documentUiState, it)
                    },
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .align(alignment = androidx.compose.ui.Alignment.CenterVertically)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkItemPreview(
    @PreviewParameter(BookmarkItemUiStateProvider::class) items: Pair<Boolean, DocumentUiState>,
) {
    val editModeUiState = items.first
    val documentUiState = items.second

    ComposeApplicationTheme {
        BookmarkItem(
            editModeUiState = editModeUiState,
            documentUiState = documentUiState,
            modifier = Modifier.fillMaxSize()
        )
    }
}