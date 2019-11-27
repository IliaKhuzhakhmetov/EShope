package ru.don.eshope

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.don.eshope.di.createDataBaseModule
import ru.don.eshope.di.createMainModule
import ru.don.eshope.di.createViewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build().also { core ->
            Fabric.with(this, Crashlytics.Builder().core(core).build())
        }

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

