package ru.lonelywh1te.justweather.presentation.state

sealed class UIState<T> {
    class Init<T>: UIState<T>()
    class Loading<T>: UIState<T>()
    data class Success<T>(val data: T): UIState<T>()
    data class Error<T>(val errorCode: Int?, val exception: Throwable?): UIState<T>()
}