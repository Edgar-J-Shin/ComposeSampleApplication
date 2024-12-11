package com.shinjh1253.data.remote.model.mapper

import com.shinjh1253.data.remote.model.RemoteDocument
import com.shinjh1253.domain.model.Document

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