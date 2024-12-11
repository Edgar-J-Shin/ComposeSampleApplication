package com.shinjh1253.data.remote.di

import com.shinjh1253.data.remote.datasource.ImageRemoteDataSource
import com.shinjh1253.data.remote.datasource.ImageRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindImageRemoteDataSource(imageRemoteDataSourceImpl: ImageRemoteDataSourceImpl): ImageRemoteDataSource
}
