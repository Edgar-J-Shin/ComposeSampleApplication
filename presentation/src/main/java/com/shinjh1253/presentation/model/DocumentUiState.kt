package com.shinjh1253.presentation.model

data class DocumentUiState(
    val collection: String,
    val datetime: String,
    val displaySitename: String,
    val docUrl: String,
    val height: Int,
    val imageUrl: String,
    val thumbnailUrl: String,
    val width: Int,
    val bookmark: Boolean,
) {
    var onBookmarkClick: ((DocumentUiState, Boolean) -> Unit)? = null

    companion object {
        val Empty = DocumentUiState(
            collection = "",
            datetime = "",
            displaySitename = "",
            docUrl = "",
            height = 0,
            imageUrl = "",
            thumbnailUrl = "",
            width = 0,
            bookmark = false
        )
    }
}