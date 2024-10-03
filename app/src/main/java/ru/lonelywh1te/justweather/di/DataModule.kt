package ru.lonelywh1te.justweather.di

import org.koin.dsl.module
import ru.lonelywh1te.justweather.BuildConfig
import ru.lonelywh1te.justweather.data.WeatherApi
import ru.lonelywh1te.justweather.data.WeatherInfoRepositoryImpl
import ru.lonelywh1te.justweather.data.weatherApi
import ru.lonelywh1te.justweather.domain.WeatherInfoRepository

val dataModule = module {
    single<WeatherApi> {
        weatherApi(BuildConfig.WEATHER_BASE_URL, BuildConfig.WEATHER_API_KEY)
    }

    single<WeatherInfoRepository>{
        WeatherInfoRepositoryImpl(weatherApi = get())
    }
}