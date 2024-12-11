package com.shinjh1253.presentation.core.ui

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface EventDelegate<EF, EV> {
    val uiEffect: SharedFlow<EF>

    suspend fun emitUiEffect(effect: EF)

    fun dispatchEvent(event: EV)

    class EventDelegateImpl<EF, EV> : EventDelegate<EF, EV> {
        private val _uiEffect = MutableSharedFlow<EF>()
        override val uiEffect = _uiEffect.asSharedFlow()

        /**
         * UI Effect Event를 전달한다.
         *
         * @param effect ViewModel 에서 View로 전달하는 UI Event
         */
        override suspend fun emitUiEffect(effect: EF) {
            _uiEffect.emit(effect)
        }

        /**
         * UI Action Event를 전달한다.
         *
         * @param event View에서 ViewModel로 전달하는 UI Action Event
         */
        override fun dispatchEvent(event: EV) {}
    }
}
