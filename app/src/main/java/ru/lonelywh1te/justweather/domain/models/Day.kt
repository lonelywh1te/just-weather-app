package ru.lonelywh1te.justweather.domain.models

data class Day(
    val maxTempC: Double,
    val maxTempF: Double,
    val minTempC: Double,
    val minTempF: Double,
    val condition: Condition,
    val uv: Double,
)