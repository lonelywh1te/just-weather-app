package ru.lonelywh1te.justweather.data

import ru.lonelywh1te.justweather.data.network.dto.search.SearchLocationDto
import ru.lonelywh1te.justweather.data.network.dto.weather.ConditionDto
import ru.lonelywh1te.justweather.data.network.dto.weather.CurrentWeatherDto
import ru.lonelywh1te.justweather.data.network.dto.weather.DayDto
import ru.lonelywh1te.justweather.data.network.dto.weather.ForecastDayDto
import ru.lonelywh1te.justweather.data.network.dto.weather.ForecastDto
import ru.lonelywh1te.justweather.data.network.dto.weather.HourDto
import ru.lonelywh1te.justweather.data.network.dto.weather.WeatherLocationDto
import ru.lonelywh1te.justweather.data.network.dto.weather.WeatherResponse
import ru.lonelywh1te.justweather.domain.models.Condition
import ru.lonelywh1te.justweather.domain.models.CurrentWeather
import ru.lonelywh1te.justweather.domain.models.Day
import ru.lonelywh1te.justweather.domain.models.Forecast
import ru.lonelywh1te.justweather.domain.models.ForecastDay
import ru.lonelywh1te.justweather.domain.models.Hour
import ru.lonelywh1te.justweather.domain.models.Location
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import java.util.Date
import kotlin.math.roundToInt

fun WeatherResponse.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        location = this.location.toLocation(),
        current = this.currentWeather.toCurrentWeather(),
        forecast = this.forecast?.toForecast(),
    )
}

fun WeatherInfo.toWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        location = this.location.toWeatherLocationDto(),
        currentWeather = this.current.toCurrentWeatherDto(),
        forecast = this.forecast?.toForecastDto(),
    )
}

fun Location.toWeatherLocationDto(): WeatherLocationDto {
    return WeatherLocationDto(
        name = this.name,
        region = this.region,
        country = this.country,
        lat = this.lat,
        lon = this.lon,
        timezoneId = "",
        localTime = Date(),
        localTimeString = Date().toString(),
    )
}


fun CurrentWeather.toCurrentWeatherDto(): CurrentWeatherDto {
    return CurrentWeatherDto(
        lastUpdated = this.lastUpdated,
        tempC = this.tempC.toDouble(),
        tempF = this.tempF.toDouble(),
        condition = this.condition.toConditionDto(),
        windKph = this.windKph,
        windMph = this.windMph,
        feelsLikeC = this.feelsLikeC.toDouble(),
        feelsLikeF = this.feelsLikeF.toDouble(),
        uv = this.uv,
    )
}

fun Forecast.toForecastDto(): ForecastDto {
    return ForecastDto(
        forecastDays = this.forecastDays.map { it.toForecastDayDto() }
    )
}

fun ForecastDay.toForecastDayDto(): ForecastDayDto {
    return ForecastDayDto(
        date = this.date,
        day = this.day.toDayDto(),
        hour = this.hour.map { it.toHourDto() }
    )
}

fun Day.toDayDto(): DayDto {
    return DayDto(
        maxTempC = this.maxTempC.toDouble(),
        maxTempF = this.maxTempF.toDouble(),
        minTempC = this.minTempC.toDouble(),
        minTempF = this.minTempF.toDouble(),
        condition = this.condition.toConditionDto(),
        uv = this.uv
    )
}

fun Hour.toHourDto(): HourDto {
    return HourDto(
        time = this.time,
        tempC = this.tempC.toDouble(),
        tempF = this.tempF.toDouble(),
        condition = this.condition.toConditionDto(),
        windKph = this.windKph,
        windMph = this.windMph,
        feelsLikeC = this.feelsLikeC.toDouble(),
        feelsLikeF = this.feelsLikeF.toDouble(),
        uv = this.uv,
        isDay = this.isDay
    )
}

fun Condition.toConditionDto(): ConditionDto {
    return ConditionDto(
        text = this.text,
        code = this.code,
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

fun WeatherLocationDto.toLocation(): Location {
    return Location(
        name = this.name,
        region = this.region,
        country = this.country,
        lat = this.lat,
        lon = this.lon,
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

fun SearchLocationDto.toLocation(): Location {
    return Location(
        name = this.name,
        region = this.region,
        country = this.country,
        lat = this.lat,
        lon = this.lon,
    )
}

fun Location.toSearchLocationDto(): SearchLocationDto {
    return SearchLocationDto(
        id = 0,
        name = this.name,
        region = this.region,
        country = this.country,
        lat = this.lat,
        lon = this.lon,
        url = ""
    )
}