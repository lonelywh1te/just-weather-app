package ru.lonelywh1te.justweather.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.WeatherInfoRepository
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.states.ResponseState

class GetCurrentWeatherInfoUseCase(private val weatherInfoRepository: WeatherInfoRepository) {
    fun execute(locationQuery: String): Flow<ResponseState<WeatherInfo>> {
        return weatherInfoRepository.getCurrentWeatherInfo(locationQuery)
    }
}