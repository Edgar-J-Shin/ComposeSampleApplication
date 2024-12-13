package com.shinjh1253.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(obj: T)

    @Delete
    suspend fun delete(obj: T)
}