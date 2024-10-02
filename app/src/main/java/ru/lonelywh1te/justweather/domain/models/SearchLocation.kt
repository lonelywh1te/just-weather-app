package ru.lonelywh1te.justweather.domain.models

data class SearchLocation(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String
)