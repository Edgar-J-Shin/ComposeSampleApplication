package com.shinjh1253.data.local.db.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shinjh1253.data.BuildConfig
import com.shinjh1253.data.local.db.core.converter.LocalDateTimeConverter
import com.shinjh1253.data.local.db.dao.BookmarkDao
import com.shinjh1253.data.local.db.model.DocumentEntity

@Database(
    entities = [(DocumentEntity::class)],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [LocalDateTimeConverter::class]
)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        private const val DB_NAME = "${BuildConfig.ROOM_DB_NAME}.db"

        fun create(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DatabaseManager::class.java,
            DB_NAME
        ).build()
    }
}