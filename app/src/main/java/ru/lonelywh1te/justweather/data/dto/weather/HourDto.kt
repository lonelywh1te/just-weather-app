package ru.lonelywh1te.justweather.data.dto.weather

import java.util.Date

data class HourDto(
    val time: Date,
    val tempC: Double,
    val tempF: Double,
    val isDay: Boolean,
    val condition: ConditionDto,
    val windKph: Double,
    val windMph: Double,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val uv: Double,
)