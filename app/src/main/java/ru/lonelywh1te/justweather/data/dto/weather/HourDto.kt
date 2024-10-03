package ru.lonelywh1te.justweather.data.dto.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lonelywh1te.justweather.data.utils.DateSerializer
import java.util.Date

@Serializable
data class HourDto(
    @SerialName("time_epoch")
    @Serializable(DateSerializer::class)
    val time: Date,

    @SerialName("temp_c")
    val tempC: Double,
    @SerialName("temp_f")
    val tempF: Double,
    @SerialName("is_day")
    val isDay: Boolean,
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