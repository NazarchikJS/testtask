package com.nazartaranyuk.testtask.di

import androidx.lifecycle.viewmodel.viewModelFactory
import com.nazartaranyuk.start_screen.StartActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val startScreenModule = module {

    /**
     * @see StartActivityViewModel
     */
    viewModel {
        StartActivityViewModel(loadGamePassUseCase = get())
    }
}