package com.shinjh1253.presentation.ui.component.searchbar

interface SearchbarEventDelegate<EV> {
    fun dispatchSearchbarEvent(event: EV)

    open class SearchbarEventDelegateImpl<EV> : SearchbarEventDelegate<EV> {

        /**
         * UI Action Event를 전달한다.
         *
         * @param event Component에서 ViewModel로 전달하는 UI Action Event
         */
        override fun dispatchSearchbarEvent(event: EV) {}
    }
}