package ru.lonelywh1te.justweather.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.lonelywh1te.justweather.domain.SearchLocationRepository
import ru.lonelywh1te.justweather.domain.models.Location

class GetLatestSearchLocationUseCase(private val searchLocationRepository: SearchLocationRepository) {
    fun execute(): Flow<Location?> {
        return searchLocationRepository.getLastSavedLocation()
    }
}