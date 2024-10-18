package ru.lonelywh1te.justweather.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.lonelywh1te.justweather.presentation.viewmodel.MainActivityViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.SearchLocationViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.WeatherFragmentViewModel

val appModule = module {

    viewModel<MainActivityViewModel> {
        MainActivityViewModel(
            getLatestSearchLocationUseCase = get()
        )
    }

    viewModel<WeatherFragmentViewModel> {
        WeatherFragmentViewModel(
            getThreeDaysForecastWeatherUseCase = get()
        )
    }

    viewModel<SearchLocationViewModel> {
        SearchLocationViewModel(
            searchLocationUseCase = get(),
            selectLocationUseCase = get(),
        )
    }

}