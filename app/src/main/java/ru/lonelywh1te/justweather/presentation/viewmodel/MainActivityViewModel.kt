package ru.lonelywh1te.justweather.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.states.ResponseState
import ru.lonelywh1te.justweather.domain.usecases.GetCurrentWeatherInfoUseCase

private const val LOG_TAG = "MainActivityViewModel"

class MainActivityViewModel(
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
                    }
                    is ResponseState.InProgress -> {
                        _currentWeatherState.value = UIState.Loading()
                    }
                }

            }
        }
    }

    init {
        getCurrentWeatherInfo("Владивосток")
    }
}

sealed class UIState<T> {
    class Init<T>: UIState<T>()
    class Loading<T>: UIState<T>()
    data class Success<T>(val weatherInfo: WeatherInfo): UIState<T>()
    data class Error<T>(val errorCode: Int?, val exception: Throwable?): UIState<T>()
}