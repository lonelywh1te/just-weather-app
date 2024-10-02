package ru.lonelywh1te.justweather.data.dto.weather

data class WeatherLocationDto(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val timezoneId: String,
    val localtime: String,
    val localtimeEpoch: Int,
)