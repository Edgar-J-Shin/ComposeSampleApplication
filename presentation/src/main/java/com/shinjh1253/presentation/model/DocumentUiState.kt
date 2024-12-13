package com.shinjh1253.presentation.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shinjh1253.presentation.core.ui.UiState
import java.time.LocalDateTime

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
    val isSelected: Boolean
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
            bookmark = false,
            isSelected = false
        )
    }
}

class BookmarkItemUiStateProvider :
    PreviewParameterProvider<Pair<Boolean, DocumentUiState>> {
    private val documentUiState = DocumentUiState(
        collection = "blog",
        datetime = LocalDateTime.now().toString(),
        displaySitename = "네이버블로그",
        docUrl = "http://blog.naver.com/jec_crabhouse/223526236563",
        height = 640,
        imageUrl = "https://ugcmk-phinf.pstatic.net/MjAyNDA3MjZfMjUz/MDAxNzIxOTg4MjgwNjIx.-j5JEEyv1yq7qx_x9jy7vc15sS1V4To5V9PCI5wgK1Mg._z03x5HYSbpBWKCu4Jpc6fAguA9A95A8XR91D6QHoq4g.PNG/photo40334154.png?type=ffn640_640",
        thumbnailUrl = "https://search2.kakaocdn.net/argon/130x130_85_c/F7dxEGFEerb",
        width = 640,
        bookmark = false,
        isSelected = false
    )

    override val values: Sequence<Pair<Boolean, DocumentUiState>>
        /**
         * 1. non checkable Item
         * 2. checkable Item
         */
        get() = sequenceOf(
            true to documentUiState,
            false to documentUiState
        )
}

class BookmarkUiStateProvider :
    PreviewParameterProvider<Pair<SearchUiState, UiState<List<DocumentUiState>>>> {

    private val documentUiState = DocumentUiState(
        collection = "blog",
        datetime = LocalDateTime.now().toString(),
        displaySitename = "네이버블로그",
        docUrl = "http://blog.naver.com/jec_crabhouse/223526236563",
        height = 640,
        imageUrl = "https://ugcmk-phinf.pstatic.net/MjAyNDA3MjZfMjUz/MDAxNzIxOTg4MjgwNjIx.-j5JEEyv1yq7qx_x9jy7vc15sS1V4To5V9PCI5wgK1Mg._z03x5HYSbpBWKCu4Jpc6fAguA9A95A8XR91D6QHoq4g.PNG/photo40334154.png?type=ffn640_640",
        thumbnailUrl = "https://search2.kakaocdn.net/argon/130x130_85_c/F7dxEGFEerb",
        width = 640,
        bookmark = false,
        isSelected = false
    )

    override val values: Sequence<Pair<SearchUiState, UiState<List<DocumentUiState>>>>
        /**
         * 1. Loading
         * 2. Error
         * 3. Success with empty data
         * 4. Success with data
         */
        get() = sequenceOf(
            SearchUiState(query = KeywordUiState("")) to UiState.Loading,
            SearchUiState(query = KeywordUiState("")) to UiState.Error(Exception("Unknown error")),
            SearchUiState(query = KeywordUiState("")) to UiState.Success(emptyList()),
            SearchUiState(query = KeywordUiState("")) to UiState.Success(
                listOf(
                    documentUiState,
                    documentUiState.copy(
                        collection = "cafe",
                        imageUrl = "http://assets.bwbx.io/images/iawT.fgBUBIw/v1/488x-1.jpg"
                    ),
                    documentUiState.copy(
                        collection = "book",
                        imageUrl = "https://resizing.flixster.com/CFmcSrRzpJORuLJaOX74gBFrPSY=/180x254/v1.bTsxMTE3NzEwMztqOzE3MTg3OzIwNDg7MTUzMDsyMTU3"
                    ),
                    documentUiState.copy(
                        collection = "cafe",
                        imageUrl = "https://blog.kakaocdn.net/dn/yJ1IG/btsGd7xIExg/yhgpKKHDW5KkvseSWxyZU0/img.jpg"
                    ),
                    documentUiState.copy(
                        collection = "book",
                        imageUrl = "https://t1.daumcdn.net/news/202311/16/koreajoongangdaily/20231116183845577whgu.jpg"
                    ),
                )
            )
        )
}