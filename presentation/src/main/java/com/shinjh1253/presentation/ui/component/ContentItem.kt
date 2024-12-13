package com.shinjh1253.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shinjh1253.presentation.R
import com.shinjh1253.presentation.core.ui.BasicImage
import com.shinjh1253.presentation.model.DocumentUiState

@Composable
fun ContentItem(
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