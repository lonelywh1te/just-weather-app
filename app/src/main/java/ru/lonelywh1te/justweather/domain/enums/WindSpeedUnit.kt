package ru.lonelywh1te.justweather.domain.enums

enum class WindSpeedUnit(val code: Int) {
    KPH(0),
    MPH(1);

    companion object {
        const val DEFAULT_CODE = 0

        fun fromCode(code: Int): WindSpeedUnit {
            return entries.find { it.code == code } ?: throw Exception("Unknown WindSpeedUnit code")
        }
    }
}