package com.shinjh1253.presentation.model.mapper

import com.shinjh1253.domain.model.Document
import com.shinjh1253.presentation.model.DocumentUiState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Document.toUiState(
    onBookmarkClick: (DocumentUiState, Boolean) -> Unit = { _, _ -> },
): DocumentUiState =
    DocumentUiState(
        collection = collection,
        datetime = datetime.toLocalDate().toString(),
        displaySitename = displaySitename,
        docUrl = docUrl,
        height = height,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        width = width,
        bookmark = bookmark,
        isSelected = false,
    ).apply {
        this.onBookmarkClick = onBookmarkClick
    }

fun DocumentUiState.toEntity(): Document =
    Document(
        collection = collection,
        datetime = LocalDateTime.parse(datetime, DateTimeFormatter.ISO_DATE_TIME),
        displaySitename = displaySitename,
        docUrl = docUrl,
        height = height,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        width = width,
        bookmark = bookmark
    )