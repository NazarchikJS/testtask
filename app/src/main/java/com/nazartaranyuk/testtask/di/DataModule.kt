package com.nazartaranyuk.testtask.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.nazartaranyuk.data.CheckGamePassRepositoryImpl
import com.nazartaranyuk.data.LoadLinkRepositoryImpl
import com.nazartaranyuk.domain.repositories.FirebaseGamePassRepository
import com.nazartaranyuk.domain.repositories.FirebaseLinkRepository
import org.koin.dsl.module

val dataModule = module {

    /**
     * @see FirebaseGamePassRepository
     * @see CheckGamePassRepositoryImpl
     */
    single<FirebaseGamePassRepository> {
        CheckGamePassRepositoryImpl(remoteConfig = get())
    }

    /**
     * @see FirebaseLinkRepository
     * @see LoadLinkRepositoryImpl
     */
    single<FirebaseLinkRepository> {
        LoadLinkRepositoryImpl(remoteConfig = get())
    }

    factory {
        Firebase.remoteConfig
    }
}