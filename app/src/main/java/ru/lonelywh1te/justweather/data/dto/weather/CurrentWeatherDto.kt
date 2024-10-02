package ru.lonelywh1te.justweather.data.dto.weather

import java.util.Date

data class CurrentWeatherDto(
    val lastUpdated: Date,
    val tempC: Double,
    val tempF: Double,
    val condition: ConditionDto,
    val windKph: Double,
    val windMph: Double,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val uv: Double,
)