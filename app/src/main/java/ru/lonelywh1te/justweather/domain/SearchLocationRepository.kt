package ru.lonelywh1te.justweather.domain

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.models.SearchLocation
import ru.lonelywh1te.justweather.domain.state.ResponseState

interface SearchLocationRepository {
    fun searchLocation(locationQuery: String): Flow<ResponseState<List<SearchLocation>>>
    fun saveLocation(searchLocation: SearchLocation)
    fun getLastSavedLocation(): Flow<SearchLocation?>
}