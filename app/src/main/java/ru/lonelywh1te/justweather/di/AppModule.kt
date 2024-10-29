package ru.lonelywh1te.justweather.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.lonelywh1te.justweather.presentation.viewmodel.MainActivityViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.SearchLocationViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.SettingsViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.WeatherFragmentViewModel

val appModule = module {

    viewModel<MainActivityViewModel> {
        MainActivityViewModel(
            getLatestSearchLocationUseCase = get(),
            selectLocationUseCase = get(),
            searchLocationUseCase = get(),
            getTemperatureUnitUseCase = get(),
            getWindSpeedUnitUseCase = get(),
        )
    }

    viewModel<WeatherFragmentViewModel> {
        WeatherFragmentViewModel(
            getThreeDaysForecastWeatherUseCase = get(),
        )
    }

    viewModel<SearchLocationViewModel> {
        SearchLocationViewModel(
            searchLocationUseCase = get(),
        )
    }

    viewModel<SettingsViewModel>() {
        SettingsViewModel(
            changeTemperatureUnitUseCase = get(),
            changeWindSpeedUnitUseCase = get(),
            getTemperatureUnitUseCase = get(),
            getWindSpeedUnitUseCase = get(),
        )
    }
}