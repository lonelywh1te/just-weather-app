package ru.lonelywh1te.justweather.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.WeatherInfoRepository
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.state.ResponseState

class GetThreeDaysForecastWeatherUseCase(private val weatherInfoRepository: WeatherInfoRepository) {
    fun execute(locationQuery: String): Flow<ResponseState<WeatherInfo>> {
        return weatherInfoRepository.getForecastWeather(locationQuery, 3)
    }
}