package com.shinjh1253.domain.model

import java.time.LocalDateTime

data class Document(
    val collection: String,
    val datetime: LocalDateTime,
    val displaySitename: String,
    val docUrl: String,
    val height: Int,
    val imageUrl: String,
    val thumbnailUrl: String,
    val width: Int,
    val bookmark: Boolean = false,
)