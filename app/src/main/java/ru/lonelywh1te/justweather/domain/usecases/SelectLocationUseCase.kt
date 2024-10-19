package ru.lonelywh1te.justweather.domain.usecases

import ru.lonelywh1te.justweather.domain.SearchLocationRepository
import ru.lonelywh1te.justweather.domain.models.Location

class SelectLocationUseCase(private val searchLocationRepository: SearchLocationRepository) {
    fun execute(searchLocation: Location) {
        searchLocationRepository.saveLocation(searchLocation)
    }
}