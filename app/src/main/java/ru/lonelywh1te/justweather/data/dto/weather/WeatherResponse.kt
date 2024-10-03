package ru.lonelywh1te.justweather.data.dto.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("location") val location: WeatherLocationDto,
    @SerialName("current") val currentWeather: CurrentWeatherDto,
    @SerialName("forecast") val forecast: ForecastDto? = null,
)