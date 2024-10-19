package ru.lonelywh1te.justweather.domain.models

// TODO: delete location?
data class WeatherInfo(
    val location: Location,
    val current: CurrentWeather,
    val forecast: Forecast?,
)