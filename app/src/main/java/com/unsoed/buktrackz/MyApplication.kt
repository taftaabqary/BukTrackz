package com.unsoed.buktrackz

import android.app.Application
import com.unsoed.buktrackz.ui.di.useCaseModule
import com.unsoed.buktrackz.ui.di.viewModelModule
import com.unsoed.core.di.databaseModule
import com.unsoed.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    useCaseModule,
                    databaseModule,
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }
}