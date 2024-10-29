package ru.lonelywh1te.justweather.data

import android.content.SharedPreferences
import ru.lonelywh1te.justweather.data.prefs.SharedPrefs
import ru.lonelywh1te.justweather.domain.SettingsRepository
import ru.lonelywh1te.justweather.domain.enums.TemperatureUnit
import ru.lonelywh1te.justweather.domain.enums.WindSpeedUnit

class SettingsRepositoryImpl(private val prefs: SharedPreferences): SettingsRepository {

    override fun getTemperatureUnit(): TemperatureUnit {
        return TemperatureUnit.fromCode(prefs.getInt(SharedPrefs.TEMP_UNIT_KEY, TemperatureUnit.DEFAULT_CODE))
    }

    override fun setTemperatureUnit(unit: TemperatureUnit) {
        prefs.edit().putInt(SharedPrefs.TEMP_UNIT_KEY, unit.code).apply()
    }

    override fun getWindSpeedUnit(): WindSpeedUnit {
        return WindSpeedUnit.fromCode(prefs.getInt(SharedPrefs.WIND_SPEED_UNIT_KEY, WindSpeedUnit.DEFAULT_CODE))
    }

    override fun setWindSpeedUnit(unit: WindSpeedUnit) {
        prefs.edit().putInt(SharedPrefs.WIND_SPEED_UNIT_KEY, unit.code).apply()
    }
}