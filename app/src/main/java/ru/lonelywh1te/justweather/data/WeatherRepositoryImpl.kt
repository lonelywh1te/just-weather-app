package ru.lonelywh1te.justweather.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.lonelywh1te.justweather.BuildConfig
import ru.lonelywh1te.justweather.data.utils.toWeatherInfo
import ru.lonelywh1te.justweather.domain.WeatherInfoRepository
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.states.ResponseState

private const val LOG_TAG = "WeatherRepositoryImpl"

// TODO: use DI

class WeatherInfoRepositoryImpl(
    private val weatherApi: WeatherApi = weatherApi(BuildConfig.WEATHER_BASE_URL, BuildConfig.WEATHER_API_KEY)
): WeatherInfoRepository {
    override fun getCurrentWeatherInfo(locationQuery: String): Flow<ResponseState<WeatherInfo>> = flow {
        emit(ResponseState.InProgress())

        try {
            val response = weatherApi.getCurrentWeather(locationQuery)

            if (response.isSuccessful) {
                val weatherInfo = response.body()?.toWeatherInfo()

                if (weatherInfo != null) {
                    emit(ResponseState.Success(weatherInfo))
                } else {
                    emit(ResponseState.Error(null, Exception("Response body is null.")))
                }
            } else {
                emit(ResponseState.Error(response.code(), null))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(null, e))
        }
    }
}