package ru.lonelywh1te.justweather.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.lonelywh1te.justweather.data.network.WeatherApi
import ru.lonelywh1te.justweather.data.network.utils.toWeatherInfo
import ru.lonelywh1te.justweather.domain.WeatherInfoRepository
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.state.ResponseState

private const val LOG_TAG = "WeatherRepositoryImpl"

class WeatherInfoRepositoryImpl(private val weatherApi: WeatherApi): WeatherInfoRepository {

    override fun getCurrentWeatherInfo(locationQuery: String): Flow<ResponseState<WeatherInfo>> = flow {
        emit(ResponseState.InProgress())

        try {
            val response = weatherApi.getForecastWeather(locationQuery, 1)

            if (response.isSuccessful) {
                val weatherInfo = response.body()?.toWeatherInfo()

                if (weatherInfo != null) {
                    emit(ResponseState.Success(weatherInfo))
                } else {
                    emit(ResponseState.Error(null, Exception("Response body is null.")))
                    Log.e(LOG_TAG, "Response body is null.")
                }
            } else {
                emit(ResponseState.Error(response.code(), null))
                Log.e(LOG_TAG, "Error code: ${response.code()}")
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(null, e))
            Log.e(LOG_TAG, "", e)
        }
    }

    override fun getForecastWeather(
        locationQuery: String,
        days: Int
    ): Flow<ResponseState<WeatherInfo>> = flow {
        emit(ResponseState.InProgress())

        try {
            val response = weatherApi.getForecastWeather(locationQuery, days)

            if (response.isSuccessful) {
                val weatherInfo = response.body()?.toWeatherInfo()

                if (weatherInfo != null) {
                    emit(ResponseState.Success(weatherInfo))
                    Log.d(LOG_TAG, days.toString())
                    Log.d(LOG_TAG, weatherInfo.forecast?.forecastDays?.size.toString())
                } else {
                    emit(ResponseState.Error(null, Exception("Response body is null.")))
                    Log.e(LOG_TAG, "Response body is null.")
                }
            } else {
                emit(ResponseState.Error(response.code(), null))
                Log.e(LOG_TAG, "Error code: ${response.code()}")
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(null, e))
            Log.e(LOG_TAG, "", e)
        }
    }
}