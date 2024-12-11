package com.shinjh1253.data.remote.model.mapper

import com.shinjh1253.data.remote.model.GetImageResponse
import com.shinjh1253.data.remote.model.RemoteDocument
import com.shinjh1253.data.remote.model.RemoteMeta
import com.shinjh1253.domain.model.Document
import com.shinjh1253.domain.model.Images
import com.shinjh1253.domain.model.Meta

fun GetImageResponse.toEntity(): Images =
    Images(
        documents = documents.map { it.toEntity() },
        meta = meta.toEntity()
    )

fun RemoteDocument.toEntity(): Document =
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

fun RemoteMeta.toEntity(): Meta =
    Meta(
        isEnd = isEnd,
        pageableCount = pageableCount,
        totalCount = totalCount
    )