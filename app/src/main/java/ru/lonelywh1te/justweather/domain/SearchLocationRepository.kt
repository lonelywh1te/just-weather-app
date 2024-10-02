package ru.lonelywh1te.justweather.domain

import ru.lonelywh1te.justweather.domain.models.SearchLocation

interface SearchLocationRepository {
    fun searchLocation(city: String): List<SearchLocation>
    fun saveLocation(searchLocation: SearchLocation)
    fun getLastSavedLocation(): SearchLocation
}