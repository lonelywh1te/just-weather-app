package ru.lonelywh1te.justweather.domain

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.models.Location
import ru.lonelywh1te.justweather.domain.state.ResponseState

interface SearchLocationRepository {
    fun searchLocation(locationQuery: String): Flow<ResponseState<List<Location>>>
    fun saveLocation(location: Location)
    fun getLastSavedLocation(): Flow<Location?>
}