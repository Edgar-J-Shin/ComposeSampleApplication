package com.shinjh1253.data.di

import com.shinjh1253.data.repository.BookmarkRepositoryImpl
import com.shinjh1253.data.repository.ImageRepositoryImpl
import com.shinjh1253.data.repository.KeywordRepositoryImpl
import com.shinjh1253.domain.repository.BookmarkRepository
import com.shinjh1253.domain.repository.ImageRepository
import com.shinjh1253.domain.repository.KeywordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository

    @Binds
    abstract fun bindBookmarkRepository(bookmarkRepositoryImpl: BookmarkRepositoryImpl): BookmarkRepository

    @Binds
    abstract fun bindKeywordRepository(keywordRepositoryImpl: KeywordRepositoryImpl): KeywordRepository
}
