package com.nazartaranyuk.testtask

import android.app.Application
import com.google.firebase.database.logging.AndroidLogger
import com.nazartaranyuk.testtask.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@TestApplication)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    gamePlayScreenModule,
                    startScreenModule,
                    webScreenModule,
                    shopScreenModule
                )
            )
        }
    }
}