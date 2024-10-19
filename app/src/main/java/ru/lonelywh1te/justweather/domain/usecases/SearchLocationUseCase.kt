package ru.lonelywh1te.justweather.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.SearchLocationRepository
import ru.lonelywh1te.justweather.domain.models.Location
import ru.lonelywh1te.justweather.domain.state.ResponseState

class SearchLocationUseCase(private val searchLocationRepository: SearchLocationRepository) {
    fun execute(locationQuery: String): Flow<ResponseState<List<Location>>> {
        return searchLocationRepository.searchLocation(locationQuery)
    }
}