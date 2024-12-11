package com.shinjh1253.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shinjh1253.data.remote.datasource.ImageRemoteDataSource
import com.shinjh1253.data.remote.model.mapper.toEntity
import com.shinjh1253.domain.model.Document
import timber.log.Timber

class ImagePagingSource(
    private val imageRemoteDataSource: ImageRemoteDataSource,
    private val query: String
) : PagingSource<Int, Document>() {

    override fun getRefreshKey(state: PagingState<Int, Document>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Document> {
        val page = params.key ?: START_PAGE_INDEX

        return try {
            val (documents, meta) = imageRemoteDataSource.getImages(
                query = query,
                page = page
            )
                .getOrThrow()
                .let { it.documents to it.meta }

            LoadResult.Page(
                data = documents.map { it.toEntity() },
                prevKey = if (page != START_PAGE_INDEX) page - 1 else null,
                nextKey = if (!meta.isEnd) page + 1 else null
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val START_PAGE_INDEX = 1
    }
}

