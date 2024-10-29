package ru.lonelywh1te.justweather.domain.usecases

import ru.lonelywh1te.justweather.domain.SettingsRepository
import ru.lonelywh1te.justweather.domain.enums.WindSpeedUnit

class ChangeWindSpeedUnitUseCase(private val settingsRepository: SettingsRepository) {
    fun execute(unit: WindSpeedUnit) {
        settingsRepository.setWindSpeedUnit(unit)
    }
}