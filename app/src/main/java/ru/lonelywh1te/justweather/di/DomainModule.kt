package ru.lonelywh1te.justweather.di

import org.koin.dsl.module
import ru.lonelywh1te.justweather.domain.usecases.GetCurrentWeatherInfoUseCase
import ru.lonelywh1te.justweather.domain.usecases.GetThreeDaysForecastWeatherUseCase
import ru.lonelywh1te.justweather.domain.usecases.SearchLocationUseCase

val domainModule = module {

    factory<GetCurrentWeatherInfoUseCase> {
        GetCurrentWeatherInfoUseCase(weatherInfoRepository = get())
    }

    factory<GetThreeDaysForecastWeatherUseCase> {
        GetThreeDaysForecastWeatherUseCase(weatherInfoRepository = get())
    }

    factory<SearchLocationUseCase> {
        SearchLocationUseCase(searchLocationRepository = get())
    }
}