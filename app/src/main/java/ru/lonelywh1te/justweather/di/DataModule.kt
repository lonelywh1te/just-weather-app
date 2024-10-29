package ru.lonelywh1te.justweather.di

import org.koin.dsl.module
import ru.lonelywh1te.justweather.BuildConfig
import ru.lonelywh1te.justweather.data.SearchLocationRepositoryImpl
import ru.lonelywh1te.justweather.data.SettingsRepositoryImpl
import ru.lonelywh1te.justweather.data.WeatherInfoRepositoryImpl
import ru.lonelywh1te.justweather.data.network.WeatherApi
import ru.lonelywh1te.justweather.data.network.weatherApi
import ru.lonelywh1te.justweather.data.prefs.SharedPrefs
import ru.lonelywh1te.justweather.domain.SearchLocationRepository
import ru.lonelywh1te.justweather.domain.SettingsRepository
import ru.lonelywh1te.justweather.domain.WeatherInfoRepository


val dataModule = module {
    single<WeatherApi> {
        weatherApi(
            baseUrl = BuildConfig.WEATHER_BASE_URL,
            apiKey = BuildConfig.WEATHER_API_KEY,
            context = get()
        )
    }

    single<WeatherInfoRepository>{
        WeatherInfoRepositoryImpl(
            weatherApi = get(),
            prefs = SharedPrefs.getWeatherPrefs(context = get())
        )
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(
            prefs = SharedPrefs.getSettingsPrefs(context = get())
        )
    }

    single<SearchLocationRepository> {
        SearchLocationRepositoryImpl(
            weatherApi = get(),
            prefs = SharedPrefs.getWeatherPrefs(context = get())
        )
    }
}