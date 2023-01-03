package com.nazartaranyuk.testtask.di

import com.nazartaranyuk.domain.usecases.LoadGamePassUseCase
import com.nazartaranyuk.domain.usecases.LoadLinkUseCase
import org.koin.dsl.module

val domainModule = module {

    /**
     * @see startScreenModule module for StartActivityViewModel
     */
    factory {
        LoadGamePassUseCase(repository = get())
    }

    /**
     * @see LoadLinkUseCase
     */
    factory {
        LoadLinkUseCase(repository = get())
    }
}