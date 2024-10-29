package ru.lonelywh1te.justweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.domain.enums.TemperatureUnit
import ru.lonelywh1te.justweather.domain.enums.WindSpeedUnit
import ru.lonelywh1te.justweather.domain.models.Location
import ru.lonelywh1te.justweather.domain.state.ResponseState
import ru.lonelywh1te.justweather.domain.usecases.GetLatestSearchLocationUseCase
import ru.lonelywh1te.justweather.domain.usecases.GetTemperatureUnitUseCase
import ru.lonelywh1te.justweather.domain.usecases.GetWindSpeedUnitUseCase
import ru.lonelywh1te.justweather.domain.usecases.SearchLocationUseCase
import ru.lonelywh1te.justweather.domain.usecases.SelectLocationUseCase
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.state.toUIState

private const val LOG_TAG = "MainActivityViewModel"

class MainActivityViewModel(
    private val getLatestSearchLocationUseCase: GetLatestSearchLocationUseCase,
    private val selectLocationUseCase: SelectLocationUseCase,
    private val searchLocationUseCase: SearchLocationUseCase,
    private val getTemperatureUnitUseCase: GetTemperatureUnitUseCase,
    private val getWindSpeedUnitUseCase: GetWindSpeedUnitUseCase,
): ViewModel() {
    private val _userLocation = MutableStateFlow<UIState<Location>>(UIState.Init)
    val userLocation: StateFlow<UIState<Location>> get() = _userLocation

    private val _foundLocation = MutableSharedFlow<UIState<Location>>()
    val foundLocation: SharedFlow<UIState<Location>> get() = _foundLocation
    
    private val _locationQuery = MutableStateFlow("")
    val locationQuery: StateFlow<String> get() = _locationQuery

    private val _temperatureUnit = MutableStateFlow(getTemperatureUnitUseCase.execute())
    val temperatureUnit: StateFlow<TemperatureUnit> get() = _temperatureUnit

    private val _windSpeedUnit = MutableStateFlow(getWindSpeedUnitUseCase.execute())
    val windSpeedUnit: StateFlow<WindSpeedUnit> get() = _windSpeedUnit

    fun setLocationQuery(locationQuery: String) {
        _locationQuery.value = locationQuery
    }

    fun selectUserLocation(location: Location) = viewModelScope.launch {
        selectLocationUseCase.execute(location)
        _userLocation.value = UIState.Success(location)
    }
    
    fun findUserLocation(lon: Double, lat: Double) = viewModelScope.launch {
        searchLocationUseCase.execute("$lon, $lat").collect { state ->
            when(state){
                is ResponseState.Success -> if (state.data.isNotEmpty()) _foundLocation.emit(UIState.Success(state.data.first()))
                is ResponseState.Error -> _foundLocation.emit(UIState.Error(state.errorCode, state.exception))
                else -> {}
            }
        }
    }

    fun updateTemperatureUnit() {
        _temperatureUnit.value = getTemperatureUnitUseCase.execute()
    }

    fun updateWindSpeedUnit() {
        _windSpeedUnit.value = getWindSpeedUnitUseCase.execute()
    }

    private fun getLastUserLocation() = viewModelScope.launch {
        getLatestSearchLocationUseCase.execute().collect { state ->
            _userLocation.value = state.toUIState()
        }
    }

    init {
        getLastUserLocation()
    }
}
