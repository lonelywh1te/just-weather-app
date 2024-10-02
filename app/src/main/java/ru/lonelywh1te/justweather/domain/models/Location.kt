package ru.lonelywh1te.justweather.domain.models

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val timezoneId: String,
    val localtime: String,
    val localtimeEpoch: Int,
)