package com.nazartaranyuk.testtask.di

import androidx.room.Room
import com.nazartaranyuk.game_play_screen.PlayActivityViewModel
import com.nazartaranyuk.data.data.GameDatabase
import com.nazartaranyuk.data.data.GameRepository
import com.nazartaranyuk.data.data.GameRepositoryImpl
import com.nazartaranyuk.data.data.LastWinDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val daoModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            GameDatabase::class.java,
            GameDatabase::class.simpleName
        ).build()
    }

    single<LastWinDAO> {
        val database = get<GameDatabase>()
        database.getDatabaseDAO()
    }
}

val repositoryModule = module {
    single<GameRepository> {
        GameRepositoryImpl(dao = get())
    }
}

val gamePlayScreenModule = module {
    includes(daoModule, repositoryModule)
    viewModel {
        PlayActivityViewModel(
            repository = get(),
            coinsRepository = get()
        )
    }
}