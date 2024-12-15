package com.shinjh1253.data.local.model.mapper

import com.shinjh1253.data.local.model.DocumentEntity
import com.shinjh1253.data.local.model.DocumentMetaEntity
import com.shinjh1253.data.local.model.LocalDocument
import com.shinjh1253.domain.model.Document

fun LocalDocument.toDto(keyword: String = ""): DocumentEntity =
    DocumentEntity(
        documentMetaEntity = toMetaDto(),
        keyword = keyword
    )

fun LocalDocument.toMetaDto(): DocumentMetaEntity =
    DocumentMetaEntity(
        collection = collection,
        datetime = datetime,
        displaySitename = displaySitename,
        docUrl = docUrl,
        height = height,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        width = width
    )

fun LocalDocument.toEntity(): Document =
    Document(
        collection = collection,
        datetime = datetime,
        displaySitename = displaySitename,
        docUrl = docUrl,
        height = height,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        width = width
    )

fun DocumentEntity.toLocal(): LocalDocument = documentMetaEntity.run {
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

fun Document.toLocal(): LocalDocument =
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
