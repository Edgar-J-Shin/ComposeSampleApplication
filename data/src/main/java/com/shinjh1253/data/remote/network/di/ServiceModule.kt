package com.shinjh1253.data.remote.network.di

import com.shinjh1253.data.remote.network.service.KakaoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideKakaoApiService(retrofit: Retrofit): KakaoApiService = retrofit.create(
        KakaoApiService::class.java)
}
