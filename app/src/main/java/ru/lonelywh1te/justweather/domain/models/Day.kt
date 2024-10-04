package ru.lonelywh1te.justweather.domain.models

data class Day(
    val maxTempC: Int,
    val maxTempF: Int,
    val minTempC: Int,
    val minTempF: Int,
    val condition: Condition,
    val uv: Double,
)