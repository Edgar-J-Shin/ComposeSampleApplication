package com.shinjh1253.data.remote.network.service

import com.shinjh1253.data.remote.model.GetImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoApiService {

    /**
     * 이미지 검색하기
     *
     * @param query 검색을 원하는 질의어
     * @param sort 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
     * @param page 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
     * @param size 한 페이지에 보여질 문서 수, 1~80 사이의 값, 기본 값 80
     *
     * @return 이미지 검색 결과
     */
    @GET("search/image")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int = 80,
    ): Response<GetImagesResponse>
}