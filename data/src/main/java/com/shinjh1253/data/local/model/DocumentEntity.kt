package com.shinjh1253.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @Embedded
    val documentMetaEntity: DocumentMetaEntity,
    val keyword: String
)

data class DocumentMetaEntity(
    val collection: String,
    val datetime: LocalDateTime,
    val displaySitename: String,
    val docUrl: String,
    val height: Int,
    val imageUrl: String,
    val thumbnailUrl: String,
    val width: Int,
)