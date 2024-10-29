package ru.lonelywh1te.justweather.data.prefs

import android.content.Context
import android.content.SharedPreferences
import ru.lonelywh1te.justweather.BuildConfig

object SharedPrefs {
    // weather keys
    const val LOCATION_KEY = "location"
    const val WEATHER_INFO_KEY = "weather_info"

    // settings keys
    const val LANGUAGE_KEY = "lang"
    const val TEMP_UNIT_KEY = "t_unit"
    const val WIND_SPEED_UNIT_KEY = "w_speed_unit"

    fun getWeatherPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.WEATHER_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun getSettingsPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.SETTINGS_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}