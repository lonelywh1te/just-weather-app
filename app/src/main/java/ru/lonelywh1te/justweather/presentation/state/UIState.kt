package ru.lonelywh1te.justweather.presentation.state

sealed class UIState<out T> {
    data object Init : UIState<Nothing>()
    data object Loading : UIState<Nothing>()
    data class Success<T>(val data: T): UIState<T>()
    data class Error(val errorCode: Int?, val exception: Throwable?): UIState<Nothing>()
}