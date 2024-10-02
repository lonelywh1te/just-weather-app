package ru.lonelywh1te.justweather.data.dto.weather

data class DayDto(
    val maxTempC: Double,
    val maxTempF: Double,
    val minTempC: Double,
    val minTempF: Double,
    val condition: ConditionDto,
    val uv: Double,
)