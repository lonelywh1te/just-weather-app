package ru.lonelywh1te.justweather.domain.models

data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>,
)