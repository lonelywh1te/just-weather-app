package ru.lonelywh1te.justweather.domain.models

import java.util.Date

data class Hour(
    val time: Date,
    val tempC: Double,
    val tempF: Double,
    val isDay: Boolean,
    val condition: Condition,
    val windKph: Double,
    val windMph: Double,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val uv: Double,
)