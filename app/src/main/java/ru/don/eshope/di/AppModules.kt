package ru.don.eshope.di

import android.content.Context
import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.don.eshope.repositories.databases.EshopDatabase
import ru.don.eshope.repositories.interactors.purchase_intercator.PurchaseInteractor
import ru.don.eshope.repositories.purchase_repo.PurchaseRepository
import ru.don.eshope.data.DataProvider
import ru.don.eshope.ui.purchases_screen.PurchasesListViewModel
import ru.don.eshope.ui.purchases_screen.PurchasesViewModel

fun createViewModelModule() = module {
    viewModel { PurchasesViewModel(get(named(DATA_PROVIDER)), get(named(PURCHASE_REPO))) }
    viewModel { PurchasesListViewModel() }
}

const val APP_CONTEXT = "ApplicationContext"
const val RESOURCES = "Resources"
const val DATA_PROVIDER = "DataProvider"
const val DATA_BASE_NAME = "eshop_database"
const val DATA_BASE = "DataBase"

const val PURCHASE_DAO = "PurchaseDao"
const val PURCHASE_REPO = "PurchaseRepository"
const val PURCHASE_INTERACT = "PurchaseInteract"

fun createMainModule(context: Context) = module {

    single(named(APP_CONTEXT)) { context }

    single(named(RESOURCES)) { context.resources }

    single(named(DATA_PROVIDER)) { DataProvider(get(named(APP_CONTEXT))) }

    single(named(DATA_BASE)) {
        Room.databaseBuilder(
            get(named(APP_CONTEXT)),
            EshopDatabase::class.java,
            DATA_BASE_NAME
        ).build()
    }

    single(named(PURCHASE_DAO)) { get<EshopDatabase>(named(DATA_BASE)).purchaseDao() }

    single(named(PURCHASE_REPO)) { PurchaseRepository(get(named(PURCHASE_DAO))) }

    single(named(PURCHASE_INTERACT)) { PurchaseInteractor(get()) }
}