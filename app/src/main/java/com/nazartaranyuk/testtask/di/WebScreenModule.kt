package com.nazartaranyuk.testtask.di

import com.nazartaranyuk.web_screen.WebViewActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val webScreenModule = module {

    /**
     * @see WebViewActivityViewModel
     */
    viewModel {
        WebViewActivityViewModel(loadLinkUseCase = get())
    }
}