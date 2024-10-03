package ru.lonelywh1te.justweather.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.lonelywh1te.justweather.presentation.viewmodel.MainActivityViewModel

val appModule = module {

    viewModel<MainActivityViewModel> {
        MainActivityViewModel(
            getCurrentWeatherInfoUseCase = get()
        )
    }

}