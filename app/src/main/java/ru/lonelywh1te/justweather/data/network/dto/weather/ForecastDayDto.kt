package ru.lonelywh1te.justweather.data.network.dto.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lonelywh1te.justweather.data.network.utils.DateSerializer
import java.util.Date

@Serializable
data class ForecastDayDto(
    @SerialName("date_epoch")
    @Serializable(DateSerializer::class)
    val date: Date,

    @SerialName("day") val day: DayDto,
    @SerialName("hour") val hour: List<HourDto>,
)