package com.shinjh1253.data.remote.datasource

import com.shinjh1253.data.remote.model.GetImagesResponse

interface ImageRemoteDataSource {
    suspend fun getImages(
        query: String,
        page: Int
    ): Result<GetImagesResponse>
}
