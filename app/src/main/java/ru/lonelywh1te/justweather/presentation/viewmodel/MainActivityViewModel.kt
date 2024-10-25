package ru.lonelywh1te.justweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.domain.models.Location
import ru.lonelywh1te.justweather.domain.state.ResponseState
import ru.lonelywh1te.justweather.domain.usecases.GetLatestSearchLocationUseCase
import ru.lonelywh1te.justweather.domain.usecases.SearchLocationUseCase
import ru.lonelywh1te.justweather.domain.usecases.SelectLocationUseCase
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.state.toUIState

private const val LOG_TAG = "MainActivityViewModel"

class MainActivityViewModel(
    private val getLatestSearchLocationUseCase: GetLatestSearchLocationUseCase,
    private val selectLocationUseCase: SelectLocationUseCase,
    private val searchLocationUseCase: SearchLocationUseCase,
): ViewModel() {
    private val _userLocation = MutableStateFlow<UIState<Location>>(UIState.Init)
    val userLocation: StateFlow<UIState<Location>> get() = _userLocation

    private val _foundLocation = MutableSharedFlow<UIState<Location>>()
    val foundLocation: SharedFlow<UIState<Location>> get() = _foundLocation
    
    private val _locationQuery = MutableStateFlow("")
    val locationQuery: StateFlow<String> get() = _locationQuery

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

    private fun getLastUserLocation() = viewModelScope.launch {
        getLatestSearchLocationUseCase.execute().collect { state ->
            _userLocation.value = state.toUIState()
        }
    }

    init {
        getLastUserLocation()
    }
}
