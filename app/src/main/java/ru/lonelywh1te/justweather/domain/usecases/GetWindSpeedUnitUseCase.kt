package ru.lonelywh1te.justweather.domain.usecases

import ru.lonelywh1te.justweather.domain.SettingsRepository
import ru.lonelywh1te.justweather.domain.enums.WindSpeedUnit

class GetWindSpeedUnitUseCase(private val settingsRepository: SettingsRepository) {
    fun execute(): WindSpeedUnit {
        return settingsRepository.getWindSpeedUnit()
    }
}