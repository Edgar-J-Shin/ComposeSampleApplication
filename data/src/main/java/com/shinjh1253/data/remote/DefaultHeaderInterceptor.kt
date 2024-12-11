package com.shinjh1253.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DefaultHeaderInterceptor
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
        "Authorization" to "KakaoAK $REST_API_KEY"
    )

    companion object {
        private const val REST_API_KEY = "163b7a7661269639cc0c14df3f6c81cf"
    }
}
