package ru.lonelywh1te.justweather.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.SearchLocationRepository
import ru.lonelywh1te.justweather.domain.models.Location
import ru.lonelywh1te.justweather.domain.state.ResponseState

class GetLatestSearchLocationUseCase(private val searchLocationRepository: SearchLocationRepository) {
    fun execute(): Flow<ResponseState<Location>> {
        return searchLocationRepository.getLastSavedLocation()
    }
}