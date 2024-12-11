package com.shinjh1253.data.remote.datasource

import com.shinjh1253.data.remote.model.GetImageResponse
import com.shinjh1253.data.remote.service.KakaoApiService
import javax.inject.Inject

class ImageRemoteDataSourceImpl
@Inject
constructor(
    private val kakaoApiService: KakaoApiService,
) : ImageRemoteDataSource {

    override suspend fun getImages(query: String, page: Int): Result<GetImageResponse> =
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

            val message = response.errorBody()?.string() ?: "unknown error"
            throw Throwable(message)
        }
}