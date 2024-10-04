package ru.lonelywh1te.justweather.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.domain.models.SearchLocation
import ru.lonelywh1te.justweather.domain.usecases.GetLatestSearchLocationUseCase

private const val LOG_TAG = "MainActivityViewModel"

class MainActivityViewModel(
    private val getLatestSearchLocationUseCase: GetLatestSearchLocationUseCase
): ViewModel() {
    private val _userLocation = MutableStateFlow<SearchLocation?>(null)
    val userLocation: StateFlow<SearchLocation?> get() = _userLocation

    private val _locationQuery = MutableStateFlow("")
    val locationQuery: StateFlow<String> get() = _locationQuery

    fun setLocationQuery(locationQuery: String) {
        _locationQuery.value = locationQuery
    }

    fun getLastUserLocation() = viewModelScope.launch {
        getLatestSearchLocationUseCase.execute().collect { location ->
            _userLocation.value = location
        }
    }

    init {
        getLastUserLocation()
    }
}
