package ru.lonelywh1te.justweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.domain.models.Location
import ru.lonelywh1te.justweather.domain.usecases.GetLatestSearchLocationUseCase
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.state.toUIState

private const val LOG_TAG = "MainActivityViewModel"

class MainActivityViewModel(
    private val getLatestSearchLocationUseCase: GetLatestSearchLocationUseCase
): ViewModel() {
    private val _userLocation = MutableStateFlow<UIState<Location>>(UIState.Init)
    val userLocation: StateFlow<UIState<Location>> get() = _userLocation

    private val _locationQuery = MutableStateFlow("")
    val locationQuery: StateFlow<String> get() = _locationQuery

    fun setLocationQuery(locationQuery: String) {
        _locationQuery.value = locationQuery
    }

    fun getLastUserLocation() = viewModelScope.launch {
        getLatestSearchLocationUseCase.execute().collect { state ->
            _userLocation.value = state.toUIState()
        }
    }

    init {
        getLastUserLocation()
    }
}
