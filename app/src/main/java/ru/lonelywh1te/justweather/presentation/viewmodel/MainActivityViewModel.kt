package ru.lonelywh1te.justweather.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.lonelywh1te.justweather.data.WeatherInfoRepositoryImpl
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.states.ResponseState
import ru.lonelywh1te.justweather.domain.usecases.GetCurrentWeatherInfoUseCase

// TODO: use DI

private const val LOG_TAG = "MainActivityViewModel"

class MainActivityViewModel(): ViewModel() {
    private val _currentWeatherState = MutableStateFlow<ResponseState<WeatherInfo>>(ResponseState.None())
    val currentWeatherState: StateFlow<ResponseState<WeatherInfo>> get() = _currentWeatherState

    private val repository = WeatherInfoRepositoryImpl()
    private val getCurrentWeatherInfoUseCase = GetCurrentWeatherInfoUseCase(repository)

    fun getCurrentWeatherInfo(locationQuery: String) {
        viewModelScope.launch {
            getCurrentWeatherInfoUseCase.execute(locationQuery).collectLatest { state ->
                when(state) {
                    is ResponseState.Success<WeatherInfo> -> {
                        Log.d(LOG_TAG, state.data.toString())
                    }
                    is ResponseState.Error -> {
                        Log.d(LOG_TAG, state.errorCode.toString())
                        Log.d(LOG_TAG, "", state.exception)
                    }

                    is ResponseState.InProgress -> {
                        Log.d(LOG_TAG, "LOADING DATA")
                    }

                    is ResponseState.None -> {}
                }

            }
        }
    }

    init {
        getCurrentWeatherInfo("Владивосток")
    }
}