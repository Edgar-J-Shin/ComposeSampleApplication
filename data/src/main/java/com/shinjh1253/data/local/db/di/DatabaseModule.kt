package com.shinjh1253.data.local.db.di

import android.content.Context
import com.shinjh1253.data.local.db.core.db.DatabaseManager
import com.shinjh1253.data.local.db.dao.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DatabaseManager =
        DatabaseManager.create(context)

    @Provides
    @Singleton
    fun provideBookmarkDao(databaseManager: DatabaseManager): BookmarkDao =
        databaseManager.bookmarkDao()
}