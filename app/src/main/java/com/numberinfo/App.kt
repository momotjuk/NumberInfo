package com.numberinfo

import android.app.Application
import com.numberinfo.core.di.dispatchersModule
import com.numberinfo.core.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@App)
            modules(koinModule, dispatchersModule)
        }
    }
}