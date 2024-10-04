package ru.lonelywh1te.justweather.di

import android.content.SharedPreferences
import org.koin.dsl.module
import ru.lonelywh1te.justweather.BuildConfig
import ru.lonelywh1te.justweather.data.SearchLocationRepositoryImpl
import ru.lonelywh1te.justweather.data.WeatherInfoRepositoryImpl
import ru.lonelywh1te.justweather.data.network.WeatherApi
import ru.lonelywh1te.justweather.data.network.weatherApi
import ru.lonelywh1te.justweather.data.prefs.WeatherPrefs
import ru.lonelywh1te.justweather.domain.SearchLocationRepository
import ru.lonelywh1te.justweather.domain.WeatherInfoRepository


val dataModule = module {
    single<WeatherApi> {
        weatherApi(BuildConfig.WEATHER_BASE_URL, BuildConfig.WEATHER_API_KEY)
    }

    single<SharedPreferences> {
        WeatherPrefs.get(context = get())
    }

    single<WeatherInfoRepository>{
        WeatherInfoRepositoryImpl(weatherApi = get())
    }

    single<SearchLocationRepository> {
        SearchLocationRepositoryImpl(weatherApi = get(), prefs = get())
    }
}