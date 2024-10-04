package ru.lonelywh1te.justweather.data.utils

import ru.lonelywh1te.justweather.data.dto.weather.ConditionDto
import ru.lonelywh1te.justweather.data.dto.weather.CurrentWeatherDto
import ru.lonelywh1te.justweather.data.dto.weather.DayDto
import ru.lonelywh1te.justweather.data.dto.weather.ForecastDayDto
import ru.lonelywh1te.justweather.data.dto.weather.ForecastDto
import ru.lonelywh1te.justweather.data.dto.weather.HourDto
import ru.lonelywh1te.justweather.data.dto.weather.WeatherLocationDto
import ru.lonelywh1te.justweather.data.dto.weather.WeatherResponse
import ru.lonelywh1te.justweather.domain.models.Condition
import ru.lonelywh1te.justweather.domain.models.CurrentWeather
import ru.lonelywh1te.justweather.domain.models.Day
import ru.lonelywh1te.justweather.domain.models.Forecast
import ru.lonelywh1te.justweather.domain.models.ForecastDay
import ru.lonelywh1te.justweather.domain.models.Hour
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.models.WeatherLocation
import kotlin.math.roundToInt

fun WeatherResponse.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        location = this.location.toWeatherLocation(),
        current = this.currentWeather.toCurrentWeather(),
        forecast = this.forecast?.toForecast(),
    )
}

fun ForecastDto.toForecast(): Forecast {
    return Forecast(
        forecastDays = this.forecastDays.map { it.toForecastDay() }
    )
}

fun ForecastDayDto.toForecastDay(): ForecastDay {
    return ForecastDay(
        date = this.date,
        day = this.day.toDay(),
        hour = this.hour.map { it.toHour() }
    )
}

fun DayDto.toDay(): Day {
    return Day(
        maxTempC = this.maxTempC.roundToInt(),
        maxTempF = this.maxTempF.roundToInt(),
        minTempC = this.minTempC.roundToInt(),
        minTempF = this.minTempF.roundToInt(),
        condition = this.condition.toCondition(),
        uv = this.uv
    )
}

fun HourDto.toHour(): Hour {
    return Hour(
        time = this.time,
        tempC = this.tempC.roundToInt(),
        tempF = this.tempF.roundToInt(),
        condition = this.condition.toCondition(),
        windKph = this.windKph,
        windMph = this.windMph,
        feelsLikeC = this.feelsLikeC.roundToInt(),
        feelsLikeF = this.feelsLikeF.roundToInt(),
        isDay = this.isDay,
        uv = this.uv
    )
}

fun WeatherLocationDto.toWeatherLocation(): WeatherLocation {
    return WeatherLocation(
        name = this.name,
        region = this.region,
        country = this.country,
        lat = this.lat,
        lon = this.lon,
        timezoneId = this.timezoneId,
        localTime = this.localTime,
    )
}

fun CurrentWeatherDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        lastUpdated = this.lastUpdated,
        tempC = this.tempC.roundToInt(),
        tempF = this.tempF.roundToInt(),
        condition = this.condition.toCondition(),
        windKph = this.windKph,
        windMph = this.windMph,
        feelsLikeC = this.feelsLikeC.roundToInt(),
        feelsLikeF = this.feelsLikeF.roundToInt(),
        uv = this.uv,
    )
}

fun ConditionDto.toCondition(): Condition {
    return Condition(
        text = this.text,
        code = this.code,
    )
}