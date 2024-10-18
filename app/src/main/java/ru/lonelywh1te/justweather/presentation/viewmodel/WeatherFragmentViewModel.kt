package ru.lonelywh1te.justweather.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.usecases.GetThreeDaysForecastWeatherUseCase
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.state.toUIState

private const val LOG_TAG = "WeatherFragmentViewModel"

class WeatherFragmentViewModel(
    private val getThreeDaysForecastWeatherUseCase: GetThreeDaysForecastWeatherUseCase,
): ViewModel() {
    private val _currentWeatherState = MutableStateFlow<UIState<WeatherInfo>>(UIState.Init)
    val currentWeatherState: StateFlow<UIState<WeatherInfo>> get() = _currentWeatherState

    fun getForecastWeatherInfo(locationQuery: String) {
        viewModelScope.launch {
            getThreeDaysForecastWeatherUseCase.execute(locationQuery).collect { state ->
                Log.d(LOG_TAG, state.toString())
                _currentWeatherState.emit(state.toUIState())
            }
        }
    }
}