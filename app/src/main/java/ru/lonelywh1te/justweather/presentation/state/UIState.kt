package ru.lonelywh1te.justweather.presentation.state

import ru.lonelywh1te.justweather.domain.state.ResponseState

sealed class UIState<out T> {
    data object Init : UIState<Nothing>()
    data class Loading<T>(val data: T? = null) : UIState<T>()
    data class Success<T>(val data: T): UIState<T>()
    data class Error<T>(val errorCode: Int?, val exception: Throwable?, val data: T? = null): UIState<T>()
}

fun <T> ResponseState<T>.toUIState(): UIState<T> {
    return when (this) {
        is ResponseState.Success -> UIState.Success(data)
        is ResponseState.Error -> UIState.Error(errorCode, exception, data)
        is ResponseState.InProgress -> UIState.Loading(data)
    }
}