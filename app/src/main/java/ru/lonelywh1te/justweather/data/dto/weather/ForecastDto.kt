package ru.lonelywh1te.justweather.data.dto.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto(
    @SerialName("forecastday") val forecastDays: List<ForecastDayDto>,
)