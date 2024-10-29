package ru.lonelywh1te.justweather.domain.usecases

import ru.lonelywh1te.justweather.domain.SettingsRepository
import ru.lonelywh1te.justweather.domain.enums.TemperatureUnit

class GetTemperatureUnitUseCase(private val settingsRepository: SettingsRepository) {
    fun execute(): TemperatureUnit {
        return settingsRepository.getTemperatureUnit()
    }
}