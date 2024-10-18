package ru.lonelywh1te.justweather.domain

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.state.ResponseState

interface WeatherInfoRepository {
    fun getForecastWeather(locationQuery: String, days: Int): Flow<ResponseState<WeatherInfo>>
    fun saveWeatherInfo(weatherInfo: WeatherInfo)
    fun getLastSavedWeatherInfo(): Flow<ResponseState<WeatherInfo>>
}