package ru.lonelywh1te.justweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.lonelywh1te.justweather.domain.enums.TemperatureUnit
import ru.lonelywh1te.justweather.domain.enums.WindSpeedUnit
import ru.lonelywh1te.justweather.domain.usecases.ChangeTemperatureUnitUseCase
import ru.lonelywh1te.justweather.domain.usecases.ChangeWindSpeedUnitUseCase
import ru.lonelywh1te.justweather.domain.usecases.GetTemperatureUnitUseCase
import ru.lonelywh1te.justweather.domain.usecases.GetWindSpeedUnitUseCase

class SettingsViewModel(
    private val getTemperatureUnitUseCase: GetTemperatureUnitUseCase,
    private val getWindSpeedUnitUseCase: GetWindSpeedUnitUseCase,
    private val changeTemperatureUnitUseCase: ChangeTemperatureUnitUseCase,
    private val changeWindSpeedUnitUseCase: ChangeWindSpeedUnitUseCase,
): ViewModel() {
    private var _temperatureUnit = MutableStateFlow(getTemperatureUnitUseCase.execute())
    val temperatureUnit: StateFlow<TemperatureUnit> get() = _temperatureUnit

    private val _windSpeedUnit = MutableStateFlow(getWindSpeedUnitUseCase.execute())
    val windSpeedUnit: StateFlow<WindSpeedUnit> get() = _windSpeedUnit

    fun changeTemperatureUnit(unit: TemperatureUnit) {
        _temperatureUnit.value = unit
        changeTemperatureUnitUseCase.execute(unit)
    }

    fun changeWindSpeedUnit(unit: WindSpeedUnit) {
        _windSpeedUnit.value = unit
        changeWindSpeedUnitUseCase.execute(unit)
    }
}