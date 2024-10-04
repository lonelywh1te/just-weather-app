package ru.lonelywh1te.justweather.data.network.dto.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayDto(
    @SerialName("maxtemp_c") val maxTempC: Double,
    @SerialName("maxtemp_f") val maxTempF: Double,
    @SerialName("mintemp_c") val minTempC: Double,
    @SerialName("mintemp_f") val minTempF: Double,
    @SerialName("condition") val condition: ConditionDto,
    @SerialName("uv") val uv: Double,
)