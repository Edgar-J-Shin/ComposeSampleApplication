package com.shinjh1253.data.di

import com.shinjh1253.data.remote.DefaultHeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideJsonConverterFactory(): Converter.Factory {
        val json =
            Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                prettyPrint = true
            }

        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(defaultHeaderInterceptor: DefaultHeaderInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addNetworkInterceptor(defaultHeaderInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            ).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        jsonConverterFactory: Converter.Factory,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://dapi.kakao.com/v2/")
            .addConverterFactory(jsonConverterFactory)
            .client(okHttpClient)
            .build()
}
