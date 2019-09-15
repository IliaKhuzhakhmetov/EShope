package ru.don.eshope.di

import android.content.Context
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.don.eshope.ui.data.DataProvider
import ru.don.eshope.ui.purchases_screen.PurchasesViewModel

fun createViewModelModule() = module {
    //viewModel { LoginViewModel(get(named(REPOSITORY)), get(named(DATA_PROVIDER))) }
    viewModel { PurchasesViewModel(get(named(DATA_PROVIDER))) }
}

const val APP_CONTEXT = "ApplicationContext"
const val RESOURCES = "Resources"
const val DATA_PROVIDER = "DataProvider"

fun createMainModule(context: Context) = module {

    single(named(APP_CONTEXT)) { context }

    single(named(RESOURCES)) { context.resources }

    single(named(DATA_PROVIDER)) { DataProvider(get(named(APP_CONTEXT))) }
}