package ru.lonelywh1te.justweather.data.dto.weather

data class ForecastDayDto(
    val date: String,
    val day: DayDto,
    val hour: List<HourDto>,
)