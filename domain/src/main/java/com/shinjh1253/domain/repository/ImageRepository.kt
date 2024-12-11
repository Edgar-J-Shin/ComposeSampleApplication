package com.shinjh1253.domain.repository

import androidx.paging.PagingData
import com.shinjh1253.domain.model.Document
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImages(query: String): Flow<PagingData<Document>>
}