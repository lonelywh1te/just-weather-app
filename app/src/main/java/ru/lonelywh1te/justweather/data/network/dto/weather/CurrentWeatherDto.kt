package ru.lonelywh1te.justweather.data.network.dto.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lonelywh1te.justweather.data.network.utils.DateSerializer
import java.util.Date

@Serializable
data class CurrentWeatherDto(
    @SerialName("last_updated_epoch")
    @Serializable(DateSerializer::class)
    val lastUpdated: Date,

    @SerialName("temp_c")
    val tempC: Double,
    @SerialName("temp_f")
    val tempF: Double,
    @SerialName("condition")
    val condition: ConditionDto,
    @SerialName("wind_kph")
    val windKph: Double,
    @SerialName("wind_mph")
    val windMph: Double,
    @SerialName("feelslike_c")
    val feelsLikeC: Double,
    @SerialName("feelslike_f")
    val feelsLikeF: Double,
    @SerialName("uv")
    val uv: Double,
)