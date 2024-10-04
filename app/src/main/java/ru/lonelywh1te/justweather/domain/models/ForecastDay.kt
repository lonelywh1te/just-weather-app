package ru.lonelywh1te.justweather.domain.models

import java.util.Date

data class ForecastDay(
    val date: Date,
    val day: Day,
    val hour: List<Hour>,
)