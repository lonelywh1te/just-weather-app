package ru.lonelywh1te.justweather.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object UiUtils {

    fun toTempPattern(temp: Int): String {
        return "$temp°"
    }

    fun toMinMaxPattern(min: Int, max: Int): String {
        return "${toTempPattern(min)} / ${toTempPattern(max)}"
    }

    fun dateFormat(date: Date, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }
}