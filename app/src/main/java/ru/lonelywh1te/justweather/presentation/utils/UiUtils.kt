package ru.lonelywh1te.justweather.presentation.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object UiUtils {

    fun toTempPattern(temp: Int): String {
        return "$temp°"
    }

    fun toMinMaxPattern(min: Int, max: Int): String {
        return "${toTempPattern(min)} / ${toTempPattern(max)}"
    }

    fun getThirdDayName(): String {
        val date = Calendar.getInstance()
        date.add(Calendar.DAY_OF_WEEK, 2)
        return date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())!!.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    fun dateFormat(date: Date, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }
}