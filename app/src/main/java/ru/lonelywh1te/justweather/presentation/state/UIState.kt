package ru.lonelywh1te.justweather.presentation.state

import ru.lonelywh1te.justweather.domain.state.ResponseState

sealed class UIState<out T> {
    data object Init : UIState<Nothing>()
    data object Loading : UIState<Nothing>()
    data class Success<T>(val data: T): UIState<T>()
    data class Error(val errorCode: Int?, val exception: Throwable?): UIState<Nothing>()
}

fun <T> ResponseState<T>.toUIState(): UIState<T> {
    return when (this) {
        is ResponseState.Success -> UIState.Success(data)
        is ResponseState.Error -> UIState.Error(errorCode, exception)
        is ResponseState.InProgress -> UIState.Loading
    }
}