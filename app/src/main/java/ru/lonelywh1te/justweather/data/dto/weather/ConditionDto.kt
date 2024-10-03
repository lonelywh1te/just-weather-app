package ru.lonelywh1te.justweather.data.dto.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConditionDto(
    @SerialName("code")
    val code: Int,
    @SerialName("text")
    val text: String,
)