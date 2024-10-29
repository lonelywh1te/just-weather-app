package ru.lonelywh1te.justweather.domain.enums

enum class TemperatureUnit(val code: Int) {
    C(0),
    F(1);

    companion object {
        const val DEFAULT_CODE = 0

        fun fromCode(code: Int): TemperatureUnit {
            return entries.find { it.code == code } ?: throw Exception("Unknown TemperatureUnit code")
        }
    }
}