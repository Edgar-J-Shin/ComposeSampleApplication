package com.shinjh1253.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shinjh1253.data.di.IoDispatcher
import com.shinjh1253.data.pagingsource.ImagePagingSource
import com.shinjh1253.data.remote.datasource.ImageRemoteDataSource
import com.shinjh1253.domain.model.Document
import com.shinjh1253.domain.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageRemoteDataSource: ImageRemoteDataSource,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
) : ImageRepository {

    override fun getImages(query: String): Flow<PagingData<Document>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 80),
            pagingSourceFactory = {
                ImagePagingSource(
                    imageRemoteDataSource = imageRemoteDataSource,
                    query = query
                )
            }
        ).flow
}