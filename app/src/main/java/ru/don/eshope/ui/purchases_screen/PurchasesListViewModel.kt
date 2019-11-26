package ru.don.eshope.ui.purchases_screen

import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.database.entities.Purchase

interface IPurchasesListViewModel {
    fun onPurchaseClick(purchase: Purchase)
}

class PurchasesListViewModel : BaseViewModel() {

    lateinit var listener: IPurchasesListViewModel

    fun onClick(purchase: Purchase) = listener.onPurchaseClick(purchase)

}