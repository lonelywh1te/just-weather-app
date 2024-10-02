package ru.lonelywh1te.justweather.data.dto.weather

data class WeatherResponse(
    val location: WeatherLocationDto,
    val currentWeather: CurrentWeatherDto,
    val forecast: ForecastDto?,
)