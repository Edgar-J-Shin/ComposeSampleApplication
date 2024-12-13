package com.shinjh1253.presentation.ui.component.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.shinjh1253.presentation.R

@Composable
fun BasicImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(id = R.string.desc_image),
    placeHolder: Painter? = painterResource(id = R.drawable.img_placeholder),
    error: Painter? = painterResource(id = R.drawable.img_placeholder),
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = placeHolder,
        error = error,
        modifier = modifier
    )
}