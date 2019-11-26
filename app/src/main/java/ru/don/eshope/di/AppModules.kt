package ru.don.eshope.di

import android.content.Context
import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.don.eshope.data.DataProvider
import ru.don.eshope.database.databases.EshopDatabase
import ru.don.eshope.database.repos.ItemRepository
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.add_purchase_screen.AddPurchasesListViewModel
import ru.don.eshope.ui.add_purchase_screen.AddPurchasesViewModel
import ru.don.eshope.ui.purchase_one_screen.OnePurchasesListViewModel
import ru.don.eshope.ui.purchase_one_screen.OnePurchasesViewModel
import ru.don.eshope.ui.purchases_screen.PurchasesListViewModel
import ru.don.eshope.ui.purchases_screen.PurchasesViewModel

fun createViewModelModule() = module {
    viewModel { PurchasesViewModel(get(named(DATA_PROVIDER)), get(named(PURCHASE_REPO))) }
    viewModel { PurchasesListViewModel() }
    viewModel { AddPurchasesViewModel(get(named(ITEM_REPO)), get(named(PURCHASE_REPO))) }
    viewModel { OnePurchasesViewModel(get(named(PURCHASE_REPO))) }
    viewModel { OnePurchasesListViewModel() }
    viewModel { AddPurchasesListViewModel() }
}

const val APP_CONTEXT = "ApplicationContext"
const val RESOURCES = "Resources"
const val DATA_PROVIDER = "DataProvider"
const val DATA_BASE_NAME = "eshop_database"
const val DATA_BASE = "DataBase"

const val PURCHASE_DAO = "PurchaseDao"
const val PURCHASE_REPO = "PurchaseRepository"
const val ITEM_DAO = "ItemDao"
const val ITEM_REPO = "ItemRepository"

fun createMainModule(context: Context) = module {

    single(named(APP_CONTEXT)) { context }

    single(named(RESOURCES)) { context.resources }

    single(named(DATA_PROVIDER)) { DataProvider(get(named(APP_CONTEXT))) }

}

fun createDataBaseModule() = module {

    single(named(DATA_BASE)) {
        Room.databaseBuilder(
            get(named(APP_CONTEXT)),
            EshopDatabase::class.java,
            DATA_BASE_NAME
        ).build()
    }

    // DAOs
    single(named(PURCHASE_DAO)) { get<EshopDatabase>(named(DATA_BASE)).purchaseDao() }
    single(named(ITEM_DAO)) { get<EshopDatabase>(named(DATA_BASE)).itemDao() }

    // Repos
    single(named(PURCHASE_REPO)) { PurchaseRepository(get(named(PURCHASE_DAO))) }
    single(named(ITEM_REPO)) { ItemRepository(get(named(ITEM_DAO))) }
}