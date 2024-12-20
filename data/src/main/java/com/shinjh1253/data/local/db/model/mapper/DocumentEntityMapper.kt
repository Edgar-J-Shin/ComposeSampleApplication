package com.shinjh1253.data.local.db.model.mapper

import com.shinjh1253.data.local.db.model.DocumentEntity
import com.shinjh1253.data.local.db.model.DocumentRawEntity
import com.shinjh1253.data.local.model.LocalDocument

fun DocumentEntity.toLocal(): LocalDocument = documentRawEntity.run {
    LocalDocument(
        collection = collection,
        datetime = datetime,
        displaySitename = displaySitename,
        docUrl = docUrl,
        height = height,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        width = width
    )
}

fun LocalDocument.toDto(keyword: String = ""): DocumentEntity =
    DocumentEntity(
        documentRawEntity = toRawDto(),
        keyword = keyword
    )

fun LocalDocument.toRawDto(): DocumentRawEntity =
    DocumentRawEntity(
        collection = collection,
        datetime = datetime,
        displaySitename = displaySitename,
        docUrl = docUrl,
        height = height,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        width = width
    )