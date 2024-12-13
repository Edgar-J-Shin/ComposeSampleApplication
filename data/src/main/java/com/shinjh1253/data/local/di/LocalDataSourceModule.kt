package com.shinjh1253.data.local.di

import com.shinjh1253.data.local.datasource.BookmarkLocalDataSource
import com.shinjh1253.data.local.datasource.BookmarkLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindBookmarkLocalDataSource(bookmarkLocalDataSourceImpl: BookmarkLocalDataSourceImpl): BookmarkLocalDataSource
}
