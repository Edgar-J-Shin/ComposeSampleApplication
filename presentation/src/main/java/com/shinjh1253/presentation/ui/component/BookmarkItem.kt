package com.shinjh1253.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shinjh1253.presentation.core.ui.BasicImage
import com.shinjh1253.presentation.model.DocumentUiState

@Composable
fun BookmarkItem(
    documentUiState: DocumentUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 4.dp)
    ) {
        BasicImage(
            imageUrl = documentUiState.thumbnailUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(140.dp)
                .aspectRatio(0.7f)
                .padding(all = 4.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Column {
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

            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                text = documentUiState.datetime,
                textAlign = TextAlign.Left,
                maxLines = 1,
                fontSize = 12.sp,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}