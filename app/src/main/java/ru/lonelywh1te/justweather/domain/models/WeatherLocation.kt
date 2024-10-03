package ru.lonelywh1te.justweather.domain.models

import java.util.Date

data class WeatherLocation(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val timezoneId: String,
    val localTime: Date,
)