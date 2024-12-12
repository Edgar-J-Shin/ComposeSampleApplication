package com.shinjh1253.presentation.model.mapper

import com.shinjh1253.domain.model.Document
import com.shinjh1253.presentation.model.DocumentUiState

fun Document.toUiState(
    onBookmarkClick: (DocumentUiState, Boolean) -> Unit = { _, _ -> },
): DocumentUiState =
    DocumentUiState(
        collection = collection,
        datetime = datetime,
        displaySitename = displaySitename,
        docUrl = docUrl,
        height = height,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        width = width,
        bookmark = bookmark
    ).apply {
        this.onBookmarkClick = onBookmarkClick
    }

fun DocumentUiState.toEntity(): Document =
    Document(
        collection = collection,
        datetime = datetime,
        displaySitename = displaySitename,
        docUrl = docUrl,
        height = height,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        width = width,
        bookmark = bookmark
    )