package com.shinjh1253.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.shinjh1253.domain.usecase.GetImagesUseCase
import com.shinjh1253.presentation.core.EventDelegate
import com.shinjh1253.presentation.model.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) :
    ViewModel(),
    EventDelegate<MainUiEffect, MainUiEvent> by EventDelegate.EventDelegateImpl() {

    private val searchKeywords = MutableStateFlow("test")

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val searchUiState = searchKeywords
        .flatMapLatest {
            getImagesUseCase("test")
                .map { pagingData -> pagingData.map { it.toUiState() } }
                .cachedIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = PagingData.empty(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    override fun dispatchEvent(event: MainUiEvent) {
    }
}
