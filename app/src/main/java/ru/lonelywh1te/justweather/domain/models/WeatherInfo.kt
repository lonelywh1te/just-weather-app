package ru.lonelywh1te.justweather.domain.models

// TODO: delete location?
data class WeatherInfo(
    //val location: WeatherLocation,
    val current: CurrentWeather,
    val forecast: Forecast?,
)