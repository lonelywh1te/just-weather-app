package ru.lonelywh1te.justweather.domain.state

sealed class ResponseState<T> {
    data class Success<T>(val data: T) : ResponseState<T>()
    data class InProgress<T>(val data: T? = null) : ResponseState<T>()
    data class Error<T>(val errorCode: Int? = null, val exception: Throwable? = null, val data: T? = null) : ResponseState<T>()
}