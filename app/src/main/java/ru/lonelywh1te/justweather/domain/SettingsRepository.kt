package ru.lonelywh1te.justweather.domain

import ru.lonelywh1te.justweather.domain.enums.TemperatureUnit
import ru.lonelywh1te.justweather.domain.enums.WindSpeedUnit

interface SettingsRepository {
    fun getTemperatureUnit(): TemperatureUnit
    fun setTemperatureUnit(unit: TemperatureUnit)
    fun getWindSpeedUnit(): WindSpeedUnit
    fun setWindSpeedUnit(unit: WindSpeedUnit)
}