package ru.lonelywh1te.justweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.domain.models.Location
import ru.lonelywh1te.justweather.domain.usecases.SearchLocationUseCase
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.state.toUIState

class SearchLocationViewModel(
    private val searchLocationUseCase: SearchLocationUseCase,
): ViewModel() {
    private val _searchState = MutableStateFlow<UIState<List<Location>>>(UIState.Init)
    val searchState: StateFlow<UIState<List<Location>>> get() = _searchState

    fun search(location: String) {
        viewModelScope.launch {
            searchLocationUseCase.execute(location).collect { state ->
                _searchState.value = state.toUIState()
            }
        }
    }
}