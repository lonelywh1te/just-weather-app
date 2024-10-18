package ru.lonelywh1te.justweather.di

import org.koin.dsl.module
import ru.lonelywh1te.justweather.domain.usecases.GetLatestSearchLocationUseCase
import ru.lonelywh1te.justweather.domain.usecases.GetThreeDaysForecastWeatherUseCase
import ru.lonelywh1te.justweather.domain.usecases.SearchLocationUseCase
import ru.lonelywh1te.justweather.domain.usecases.SelectLocationUseCase

val domainModule = module {

    factory<GetThreeDaysForecastWeatherUseCase> {
        GetThreeDaysForecastWeatherUseCase(weatherInfoRepository = get())
    }

    factory<SearchLocationUseCase> {
        SearchLocationUseCase(searchLocationRepository = get())
    }

    factory<SelectLocationUseCase> {
        SelectLocationUseCase(searchLocationRepository = get())
    }

    factory<GetLatestSearchLocationUseCase> {
        GetLatestSearchLocationUseCase(searchLocationRepository = get())
    }
}