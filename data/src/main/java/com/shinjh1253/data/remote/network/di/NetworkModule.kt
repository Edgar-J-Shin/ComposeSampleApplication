package com.shinjh1253.data.remote.network.di

import com.shinjh1253.data.remote.network.core.interceptor.HeaderInterceptor
import com.shinjh1253.data.remote.network.core.serializer.LocalDateTimeSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideJsonConverterFactory(): Converter.Factory {
        val module = SerializersModule {
            contextual(LocalDateTime::class, LocalDateTimeSerializer)
        }

        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            prettyPrint = true
            serializersModule = module
        }

        return json.asConverterFactory(CONTENT_TYPE.toMediaType())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        jsonConverterFactory: Converter.Factory,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(jsonConverterFactory)
        .client(okHttpClient).build()

    companion object {
        // Type
        private const val CONTENT_TYPE = "application/json"

        // URL
        private const val HOST_URL = "https://dapi.kakao.com"
        private const val VERSION = "v2"
        private const val BASE_URL = "$HOST_URL/$VERSION/"
    }
}
