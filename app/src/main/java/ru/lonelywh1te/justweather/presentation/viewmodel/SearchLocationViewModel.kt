package ru.lonelywh1te.justweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.domain.models.SearchLocation
import ru.lonelywh1te.justweather.domain.usecases.SearchLocationUseCase
import ru.lonelywh1te.justweather.domain.usecases.SelectLocationUseCase
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.state.toUIState

class SearchLocationViewModel(
    private val searchLocationUseCase: SearchLocationUseCase,
    private val selectLocationUseCase: SelectLocationUseCase,
): ViewModel() {
    private val _searchState = MutableStateFlow<UIState<List<SearchLocation>>>(UIState.Init)
    val searchState: StateFlow<UIState<List<SearchLocation>>> get() = _searchState

    fun search(location: String) {
        viewModelScope.launch {
            searchLocationUseCase.execute(location).collect { state ->
                _searchState.value = state.toUIState()
            }
        }
    }

    fun select(location: SearchLocation) = viewModelScope.launch {
        selectLocationUseCase.execute(location)
    }
}