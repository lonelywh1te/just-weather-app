package ru.lonelywh1te.justweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val LOG_TAG = "MainActivityViewModel"

class MainActivityViewModel: ViewModel() {
    private val _locationQuery = MutableStateFlow("")
    val locationQuery: StateFlow<String> get() = _locationQuery

    fun setLocationQuery(locationQuery: String) = viewModelScope.launch {
        _locationQuery.emit(locationQuery)
    }
}
