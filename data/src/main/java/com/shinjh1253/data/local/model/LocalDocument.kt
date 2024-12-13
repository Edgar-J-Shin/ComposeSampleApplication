package com.shinjh1253.data.local.model

import java.time.LocalDateTime

data class LocalDocument(
    val collection: String,
    val datetime: LocalDateTime,
    val displaySitename: String,
    val docUrl: String,
    val height: Int,
    val imageUrl: String,
    val thumbnailUrl: String,
    val width: Int,
)