package com.shinjh1253.presentation.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData

@Stable
data class SearchUiState(
    val query: KeywordUiState,
) {
    fun queryNotEmpty() = query.keyword.isNotEmpty()

    companion object {
        val Empty = SearchUiState(
            query = KeywordUiState(""),
        )
    }
}

class SearchUiStateProvider :
    PreviewParameterProvider<Pair<SearchUiState, PagingData<DocumentUiState>>> {

    private val documentUiState = DocumentUiState(
        collection = "blog",
        datetime = "2024-07-26T13:42:00.000+09:00",
        displaySitename = "네이버블로그",
        docUrl = "http://blog.naver.com/jec_crabhouse/223526236563",
        height = 640,
        imageUrl = "https://ugcmk-phinf.pstatic.net/MjAyNDA3MjZfMjUz/MDAxNzIxOTg4MjgwNjIx.-j5JEEyv1yq7qx_x9jy7vc15sS1V4To5V9PCI5wgK1Mg._z03x5HYSbpBWKCu4Jpc6fAguA9A95A8XR91D6QHoq4g.PNG/photo40334154.png?type=ffn640_640",
        thumbnailUrl = "https://search2.kakaocdn.net/argon/130x130_85_c/F7dxEGFEerb",
        width = 640,
        bookmark = false
    )

    /**
     * 1. Init
     * 2. Loading
     * 3. Error
     * 4. Empty Data
     * 5. Data
     */
    override val values: Sequence<Pair<SearchUiState, PagingData<DocumentUiState>>>
        get() = sequenceOf(
            SearchUiState(query = KeywordUiState("")) to
                    PagingData.from(
                        data = emptyList(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Loading,
                            prepend = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                        )
                    ),
            SearchUiState(query = KeywordUiState("test1")) to
                    PagingData.from(
                        data = emptyList(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Loading,
                            prepend = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                        )
                    ),
            SearchUiState(query = KeywordUiState("test2")) to PagingData.from(
                data = emptyList(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(Exception("Unknown error")),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                )
            ),
            SearchUiState(query = KeywordUiState("test3")) to PagingData.from(
                data = emptyList(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                )
            ),
            SearchUiState(query = KeywordUiState("test4")) to PagingData.from(
                data = listOf(
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
                ),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                )
            ),
        )
}
