package com.shinjh1253.data.remote.datasource

import com.shinjh1253.data.remote.model.GetImageResponse

interface ImageRemoteDataSource {
    suspend fun getImages(
        query: String,
        page: Int
    ): Result<GetImageResponse>
}
