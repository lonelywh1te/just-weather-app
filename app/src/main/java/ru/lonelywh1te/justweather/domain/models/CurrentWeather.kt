package ru.lonelywh1te.justweather.domain.models

import java.util.Date

data class CurrentWeather(
    val lastUpdated: Date,
    val tempC: Int,
    val tempF: Int,
    val condition: Condition,
    val windKph: Double,
    val windMph: Double,
    val feelsLikeC: Int,
    val feelsLikeF: Int,
    val uv: Double,
)