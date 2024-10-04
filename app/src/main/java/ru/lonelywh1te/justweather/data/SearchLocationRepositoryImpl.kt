package ru.lonelywh1te.justweather.data

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import ru.lonelywh1te.justweather.data.network.WeatherApi
import ru.lonelywh1te.justweather.data.network.utils.toSearchLocation
import ru.lonelywh1te.justweather.data.prefs.WeatherPrefs
import ru.lonelywh1te.justweather.domain.SearchLocationRepository
import ru.lonelywh1te.justweather.domain.models.SearchLocation
import ru.lonelywh1te.justweather.domain.state.ResponseState

private const val LOG_TAG = "SearchLocationRepositoryImpl"

class SearchLocationRepositoryImpl(
    val weatherApi: WeatherApi,
    val prefs: SharedPreferences,
    ): SearchLocationRepository {
    override fun searchLocation(locationQuery: String): Flow<ResponseState<List<SearchLocation>>> = flow {
        emit(ResponseState.InProgress())

        try {
            val response = weatherApi.searchLocation(locationQuery)

            if (response.isSuccessful) {
                val locations = response.body()?.map { it.toSearchLocation() }

                if (locations != null) {
                    emit(ResponseState.Success(locations))
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

    // TODO: States
    override fun saveLocation(searchLocation: SearchLocation) {
        val location = Json.encodeToString(serializer(), searchLocation)

        prefs.edit()
            .putString(WeatherPrefs.LOCATION_KEY, location)
            .apply()
    }

    override fun getLastSavedLocation(): Flow<SearchLocation?> = flow {
        val location = prefs.getString(WeatherPrefs.LOCATION_KEY, null)

        if (location != null) {
            emit(Json.decodeFromString(location))
        } else {
            emit(null)
        }

    }
}