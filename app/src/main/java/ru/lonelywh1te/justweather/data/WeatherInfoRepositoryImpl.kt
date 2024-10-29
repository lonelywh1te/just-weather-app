package ru.lonelywh1te.justweather.data

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import ru.lonelywh1te.justweather.data.network.WeatherApi
import ru.lonelywh1te.justweather.data.network.dto.weather.WeatherResponse
import ru.lonelywh1te.justweather.data.prefs.SharedPrefs
import ru.lonelywh1te.justweather.domain.WeatherInfoRepository
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.domain.state.ResponseState

private const val LOG_TAG = "WeatherRepositoryImpl"

class WeatherInfoRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val prefs: SharedPreferences
): WeatherInfoRepository {

    override fun getForecastWeather(
        locationQuery: String,
        days: Int
    ): Flow<ResponseState<WeatherInfo>>  {
        val savedWeatherInfo = getLastSavedWeatherInfo()
        val getForecastWeatherFromApi = getForecastFromApi(locationQuery, days)

        return combine(savedWeatherInfo, getForecastWeatherFromApi) { localState, apiState ->
            when {
                localState is ResponseState.Success && apiState is ResponseState.Error -> ResponseState.Error(apiState.errorCode, apiState.exception, localState.data)
                localState is ResponseState.Success && apiState is ResponseState.InProgress -> ResponseState.InProgress(localState.data)
                else -> apiState
            }
        }
    }

    override fun saveWeatherInfo(weatherInfo: WeatherInfo) {
        val weatherResponse = weatherInfo.toWeatherResponse()
        val weatherResponseJson = Json.encodeToString(serializer(), weatherResponse)

        prefs.edit()
            .putString(SharedPrefs.WEATHER_INFO_KEY, weatherResponseJson)
            .apply()
    }

    private fun getForecastFromApi(locationQuery: String, days: Int): Flow<ResponseState<WeatherInfo>> = flow {
        try {
            emit(ResponseState.InProgress())
            val response = weatherApi.getForecastWeather(locationQuery, days)

            if (response.isSuccessful) {
                val weatherInfo = response.body()?.toWeatherInfo()

                if (weatherInfo != null) {
                    saveWeatherInfo(weatherInfo)
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

    override fun getLastSavedWeatherInfo(): Flow<ResponseState<WeatherInfo>> = flow {
        val weatherResponseJson = prefs.getString(SharedPrefs.WEATHER_INFO_KEY, null)

        if (weatherResponseJson != null) {
            val weatherResponse = Json.decodeFromString<WeatherResponse>(serializer(), weatherResponseJson)
            val weatherInfo = weatherResponse.toWeatherInfo()
            emit(ResponseState.Success(weatherInfo))
        } else {
            emit(ResponseState.Error(null, Exception("Location data not found.")))
        }
    }
}