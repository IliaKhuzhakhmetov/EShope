package ru.don.eshope

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import ru.don.eshope.di.DATA_PROVIDER
import ru.don.eshope.di.createMainModule
import ru.don.eshope.di.createViewModelModule
import ru.don.eshope.ui.data.DataProvider

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(
                listOf(
                    createMainModule(this@App),
                    createViewModelModule()
                )
            )
        }
    }
}

