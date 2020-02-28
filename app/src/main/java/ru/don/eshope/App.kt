package ru.don.eshope

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.don.eshope.di.createDataBaseModule
import ru.don.eshope.di.createMainModule
import ru.don.eshope.di.createViewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(
                listOf(
                    createMainModule(this@App),
                    createViewModelModule(),
                    createDataBaseModule()
                )
            )
        }
    }
}

