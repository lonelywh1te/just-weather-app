package ru.lonelywh1te.justweather.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.lonelywh1te.justweather.presentation.viewmodel.MainActivityViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.WeatherFragmentViewModel

val appModule = module {

    viewModel<MainActivityViewModel> {
        MainActivityViewModel()
    }

    viewModel<WeatherFragmentViewModel> {
        WeatherFragmentViewModel(
            getCurrentWeatherInfoUseCase = get()
        )
    }

}