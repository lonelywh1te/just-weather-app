package ru.lonelywh1te.justweather.domain.usecases

import ru.lonelywh1te.justweather.domain.SettingsRepository
import ru.lonelywh1te.justweather.domain.enums.TemperatureUnit

class ChangeTemperatureUnitUseCase(private val settingsRepository: SettingsRepository) {
    fun execute(unit: TemperatureUnit) {
        return settingsRepository.setTemperatureUnit(unit)
    }
}