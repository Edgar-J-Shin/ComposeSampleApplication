package com.shinjh1253.presentation.ui.bookmark

import androidx.lifecycle.ViewModel
import com.shinjh1253.domain.usecase.GetImagesUseCase
import com.shinjh1253.presentation.core.ui.EventDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) :
    ViewModel(),
    EventDelegate<BookmarkUiEffect, BookmarkUiEvent> by EventDelegate.EventDelegateImpl() {

    override fun dispatchEvent(event: BookmarkUiEvent) {

    }
}
