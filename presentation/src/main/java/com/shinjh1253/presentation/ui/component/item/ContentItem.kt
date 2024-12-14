package com.shinjh1253.presentation.ui.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.model.ContentItemUiStateProvider
import com.shinjh1253.presentation.model.DocumentUiState
import com.shinjh1253.presentation.ui.component.image.BasicImage
import com.shinjh1253.presentation.ui.theme.ComposeApplicationTheme

@Composable
fun ContentItem(
    documentUiState: DocumentUiState,
    modifier: Modifier = Modifier,
    onBookmarkClick: ((DocumentUiState, Boolean) -> Unit) = { _, _ -> }
) {
    Column(
        modifier = modifier
            .padding(all = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
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
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
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
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                )
            }

            Icon(
                painter = painterResource(
                    id =
                    if (documentUiState.bookmark) R.drawable.ic_bookmark_on else R.drawable.ic_bookmark_off
                ),
                contentDescription = stringResource(id = R.string.desc_bookmark),
                modifier = Modifier
                    .padding(all = 4.dp)
                    .align(alignment = androidx.compose.ui.Alignment.CenterVertically)
                    .clickable {
                        onBookmarkClick(documentUiState, !documentUiState.bookmark)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentItemPreview(
    @PreviewParameter(ContentItemUiStateProvider::class) items: DocumentUiState,
) {
    ComposeApplicationTheme {
        ContentItem(
            documentUiState = items,
            modifier = Modifier.fillMaxSize()
        )
    }
}