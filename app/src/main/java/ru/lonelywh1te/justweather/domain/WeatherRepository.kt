package ru.lonelywh1te.justweather.domain

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.states.ResponseState

interface WeatherInfoRepository {
    fun getCurrentWeatherInfo(locationQuery: String): Flow<ResponseState<WeatherInfo>>
}