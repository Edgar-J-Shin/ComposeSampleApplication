package com.shinjh1253.domain.usecase

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.shinjh1253.domain.model.Document
import com.shinjh1253.domain.repository.ImageRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@Reusable
class GetImagesUseCase
@Inject constructor(
    private val imageRepository: ImageRepository,
) {
    operator fun invoke(query: String): Flow<PagingData<Document>> {
        val pagingDataForEmptyQuery =
            PagingData.from(
                data = emptyList<Document>(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )

        return if (query.isEmpty()) {
            flowOf(pagingDataForEmptyQuery)
        } else {
            imageRepository.getImages(query)
        }
    }
}
