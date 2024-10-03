package ru.lonelywh1te.justweather.di

import org.koin.dsl.module
import ru.lonelywh1te.justweather.domain.usecases.GetCurrentWeatherInfoUseCase

val domainModule = module {

    factory <GetCurrentWeatherInfoUseCase> {
        GetCurrentWeatherInfoUseCase(weatherInfoRepository = get())
    }
}