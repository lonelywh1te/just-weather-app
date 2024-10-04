package ru.lonelywh1te.justweather.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.SearchLocationRepository
import ru.lonelywh1te.justweather.domain.models.SearchLocation

class GetLatestSearchLocationUseCase(private val searchLocationRepository: SearchLocationRepository) {
    fun execute(): Flow<SearchLocation?> {
        return searchLocationRepository.getLastSavedLocation()
    }
}