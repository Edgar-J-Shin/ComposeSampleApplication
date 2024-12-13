package com.shinjh1253.data.local.di

import android.content.Context
import com.shinjh1253.data.local.MyLibraryDatabase
import com.shinjh1253.data.local.dao.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyLibraryDatabase =
        MyLibraryDatabase.create(context)

    @Provides
    @Singleton
    fun provideBookmarkDao(myLibraryDatabase: MyLibraryDatabase): BookmarkDao =
        myLibraryDatabase.bookmarkDao()
}