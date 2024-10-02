package ru.lonelywh1te.justweather.domain.models

data class WeatherInfo(
    val location: Location,
    val current: CurrentWeather,
    val forecast: Forecast?,
)