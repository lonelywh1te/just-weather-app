package ru.lonelywh1te.justweather.data.dto.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lonelywh1te.justweather.data.utils.DateSerializer
import java.util.Date

@Serializable
data class WeatherLocationDto(
    @SerialName("name") val name: String,
    @SerialName("region") val region: String,
    @SerialName("country") val country: String,
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("tz_id") val timezoneId: String,
    @SerialName("localtime") val localTimeString: String,

    @SerialName("localtime_epoch")
    @Serializable(DateSerializer::class)
    val localTime: Date,
)