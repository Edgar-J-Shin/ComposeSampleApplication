package com.shinjh1253.data.remote.network.core.interceptor

import com.shinjh1253.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor
@Inject
constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val url = chain.request().url
        val urlBuilder = url.newBuilder()

        getDefaultHeader().forEach { (key, value) ->
            builder.addHeader(key, value)
        }

        return chain.proceed(
            builder
                .url(urlBuilder.build())
                .build(),
        )
    }

    private fun getDefaultHeader(): Map<String, String> = mapOf(
        KEY_AUTHORIZATION to AUTHORIZATION_KAKAO
    )

    companion object {
        // Key
        private const val KEY_AUTHORIZATION = "Authorization"

        // Value
        private const val AUTHORIZATION_KAKAO = "KakaoAK ${BuildConfig.KAKAO_API_KEY}"
    }
}
