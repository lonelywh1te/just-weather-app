package ru.lonelywh1te.justweather.domain.models

import java.util.Date

data class CurrentWeather(
    val lastUpdated: Date,
    val tempC: Double,
    val tempF: Double,
    val condition: Condition,
    val windKph: Double,
    val windMph: Double,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val uv: Double,
)