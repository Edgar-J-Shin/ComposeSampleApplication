package com.shinjh1253.domain.usecase

import androidx.paging.PagingData
import com.shinjh1253.domain.model.Document
import com.shinjh1253.domain.repository.ImageRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetImagesUseCase
@Inject constructor(
    private val imageRepository: ImageRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Document>> {
        return imageRepository.getImages(query)
    }
}
