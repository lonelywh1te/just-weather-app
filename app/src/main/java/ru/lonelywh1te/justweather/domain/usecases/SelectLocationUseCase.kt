package ru.lonelywh1te.justweather.domain.usecases

import ru.lonelywh1te.justweather.domain.SearchLocationRepository
import ru.lonelywh1te.justweather.domain.models.SearchLocation

class SelectLocationUseCase(private val searchLocationRepository: SearchLocationRepository) {
    fun execute(searchLocation: SearchLocation) {
        searchLocationRepository.saveLocation(searchLocation)
    }
}