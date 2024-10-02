package ru.lonelywh1te.justweather.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.lonelywh1te.justweather.data.dto.search.SearchResponse
import ru.lonelywh1te.justweather.data.dto.weather.WeatherResponse

interface WeatherApi {

    @GET("/current.json")
    fun getCurrentWeather(@Query("q") city: String): Response<WeatherResponse>

    @GET("/forecast.json")
    fun getForecastWeather(
        @Query("q") city: String,
        @Query("days") days: Int,
    ): Response<WeatherResponse>

    @GET("/search.json")
    fun searchCity(@Query("q") city: String): Response<SearchResponse>

}