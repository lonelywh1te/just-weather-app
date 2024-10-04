package ru.lonelywh1te.justweather.di

import org.koin.dsl.module
import ru.lonelywh1te.justweather.domain.usecases.GetCurrentWeatherInfoUseCase
import ru.lonelywh1te.justweather.domain.usecases.GetThreeDaysForecastWeatherUseCase

val domainModule = module {

    factory<GetCurrentWeatherInfoUseCase> {
        GetCurrentWeatherInfoUseCase(weatherInfoRepository = get())
    }

    factory<GetThreeDaysForecastWeatherUseCase> {
        GetThreeDaysForecastWeatherUseCase(weatherInfoRepository = get())
    }
}