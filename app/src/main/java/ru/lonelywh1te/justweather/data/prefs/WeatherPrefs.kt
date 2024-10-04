package ru.lonelywh1te.justweather.data.prefs

import android.content.Context
import android.content.SharedPreferences
import ru.lonelywh1te.justweather.BuildConfig

object WeatherPrefs {
    const val LOCATION_KEY = "location"

    fun get(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.WEATHER_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}