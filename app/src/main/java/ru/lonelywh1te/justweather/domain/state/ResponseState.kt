package ru.lonelywh1te.justweather.domain.state

sealed class ResponseState<T> {
    class Success<T>(val data: T) : ResponseState<T>()
    class Error<T>(val errorCode: Int?, val exception: Throwable?) : ResponseState<T>()
    class InProgress<T> : ResponseState<T>()
}