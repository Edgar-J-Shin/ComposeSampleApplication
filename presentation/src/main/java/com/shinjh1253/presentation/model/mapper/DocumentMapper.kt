package com.shinjh1253.presentation.model.mapper

import com.shinjh1253.domain.model.Document
import com.shinjh1253.presentation.model.DocumentUiState

fun Document.toUiState(): DocumentUiState =
    DocumentUiState(
        collection = collection,
        datetime = datetime,
        displaySitename = displaySitename,
        docUrl = docUrl,
        height = height,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        width = width,
        bookmark = bookmark,
        isSelected = false,
    )

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