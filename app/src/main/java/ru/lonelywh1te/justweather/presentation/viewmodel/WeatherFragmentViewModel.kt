package ru.lonelywh1te.justweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.state.ResponseState
import ru.lonelywh1te.justweather.domain.usecases.GetCurrentWeatherInfoUseCase
import ru.lonelywh1te.justweather.presentation.state.UIState

class WeatherFragmentViewModel(
    private val getCurrentWeatherInfoUseCase: GetCurrentWeatherInfoUseCase
): ViewModel() {
    private val _currentWeatherState = MutableStateFlow<UIState<WeatherInfo>>(UIState.Init())
    val currentWeatherState: StateFlow<UIState<WeatherInfo>> get() = _currentWeatherState

    fun getCurrentWeatherInfo(locationQuery: String) {
        viewModelScope.launch {
            getCurrentWeatherInfoUseCase.execute(locationQuery).collectLatest { state ->

                when(state) {
                    is ResponseState.Success<WeatherInfo> -> {
                        _currentWeatherState.value = UIState.Success(state.data)
                    }
                    is ResponseState.Error -> {
                        _currentWeatherState.value = UIState.Error(state.errorCode, state.exception)
                        println(state.exception)
                    }
                    is ResponseState.InProgress -> {
                        _currentWeatherState.value = UIState.Loading()
                    }
                }

            }
        }
    }
}