package ru.lonelywh1te.justweather.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import ru.lonelywh1te.justweather.data.dto.search.SearchResponse
import ru.lonelywh1te.justweather.data.dto.weather.WeatherResponse

interface WeatherApi {

    @GET("current.json")
    suspend fun getCurrentWeather(@Query("q") locationQuery: String): Response<WeatherResponse>

    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("q") locationQuery: String,
        @Query("days") days: Int,
    ): Response<WeatherResponse>

    @GET("search.json")
    suspend fun searchCity(@Query("q") locationQuery: String): Response<SearchResponse>

}

fun weatherApi(baseUrl: String, apiKey: String): WeatherApi {
    val contentType = MediaType.get("application/json")

    val client = OkHttpClient.Builder()
        .addInterceptor(WeatherApiKeyInterceptor(apiKey))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    return retrofit.create<WeatherApi>()
}