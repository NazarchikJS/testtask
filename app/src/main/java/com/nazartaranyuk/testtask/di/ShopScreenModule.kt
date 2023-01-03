package com.nazartaranyuk.testtask.di

import androidx.room.Room
import com.nazartaranyuk.data.data.*
import com.nazartaranyuk.game_shop_screen.ShopActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            CoinsDatabase::class.java,
            CoinsDatabase::class.simpleName
        ).build()
    }

    single {
        val database = get<CoinsDatabase>()
        database.getDatabaseDAO()
    }
}

val shopScreenModule = module {
    includes(databaseModule)

    single<CoinsRepository> {
        CoinsRepositoryImpl(dao = get())
    }

    viewModel {
        ShopActivityViewModel(repository = get())
    }
}
