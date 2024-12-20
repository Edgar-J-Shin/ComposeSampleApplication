package com.shinjh1253.data.remote.model

import com.shinjh1253.data.remote.network.core.serializer.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class GetImagesResponse(
    val documents: List<RemoteDocument>,
    val meta: RemoteMeta,
)

@Serializable
data class RemoteDocument(
    val collection: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val datetime: LocalDateTime,
    @SerialName("display_sitename")
    val displaySitename: String,
    @SerialName("doc_url")
    val docUrl: String,
    val height: Int,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    val width: Int,
)

@Serializable
data class RemoteMeta(
    @SerialName("is_end")
    val isEnd: Boolean,
    @SerialName("pageable_count")
    val pageableCount: Int,
    @SerialName("total_count")
    val totalCount: Int,
)