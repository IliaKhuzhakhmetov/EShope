package ru.don.eshope.di

import android.content.Context
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.don.eshope.ui.purchases_screen.PurchasesViewModel

fun createViewModelModule() = module {
    //viewModel { LoginViewModel(get(named(REPOSITORY)), get(named(DATA_PROVIDER))) }
    viewModel { PurchasesViewModel() }
}

const val APP_CONTEXT = "ApplicationContext"
const val RESOURCES = "Resources"

fun createMainModule(context: Context) = module {

    single(named(APP_CONTEXT)) { context }

    single(named(RESOURCES)) { context.resources }
}