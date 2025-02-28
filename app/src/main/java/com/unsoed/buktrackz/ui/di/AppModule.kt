package com.unsoed.buktrackz.ui.di

import com.unsoed.buktrackz.MainViewModel
import com.unsoed.buktrackz.core.domain.usecase.BookInteractor
import com.unsoed.buktrackz.core.domain.usecase.BookUseCase
import com.unsoed.buktrackz.ui.add.AddBookViewModel
import com.unsoed.buktrackz.ui.detail.DetailBookViewModel
import com.unsoed.buktrackz.ui.discover.DiscoverViewModel
import com.unsoed.buktrackz.ui.home.HomeViewModel
import com.unsoed.buktrackz.ui.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<BookUseCase> {
        BookInteractor(get())
    }
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        DetailBookViewModel(get())
    }
    viewModel {
        AddBookViewModel(get())
    }
    viewModel {
        DiscoverViewModel(get())
    }
    viewModel {
        SettingViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }
}