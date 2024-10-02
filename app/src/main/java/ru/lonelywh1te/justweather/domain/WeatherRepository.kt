package ru.lonelywh1te.justweather.domain

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.models.WeatherInfo

interface WeatherRepository {
    fun getCurrentWeatherInfo(city: String): Flow<WeatherInfo>
}