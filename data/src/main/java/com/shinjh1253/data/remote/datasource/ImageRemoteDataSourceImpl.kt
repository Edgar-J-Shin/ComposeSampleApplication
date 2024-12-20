package com.shinjh1253.data.remote.datasource

import com.shinjh1253.data.remote.model.GetImagesResponse
import com.shinjh1253.data.remote.network.service.KakaoApiService
import javax.inject.Inject

class ImageRemoteDataSourceImpl
@Inject
constructor(
    private val kakaoApiService: KakaoApiService,
) : ImageRemoteDataSource {

    override suspend fun getImages(
        query: String,
        page: Int
    ): Result<GetImagesResponse> =
        runCatching {
            val response = kakaoApiService.getImages(
                query = query,
                page = page
            )

            if (response.isSuccessful) {
                response.body()?.let { result ->
                    return@runCatching result
                }
            }

            val errorMessage = response.errorBody()?.string() ?: "unknown error"
            throw Throwable(errorMessage)
        }
}