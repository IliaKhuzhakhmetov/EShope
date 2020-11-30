package ru.don.eshope.ui.purchases_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.don.eshope.ui.base.BaseViewModel
import ru.don.eshope.data.DataProvider
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.adapter.RecyclerViewAdapterPurchases
import ru.don.eshope.utils.getGrouped

interface IPurchasesListViewModel {
    fun onPurchaseClick(purchase: Purchase)
}

class PurchasesViewModel(
    data: DataProvider,
    private val purchaseRepository: PurchaseRepository,
    val context: Context
) :
    BaseViewModel() {

    companion object {
        val TAG = PurchasesViewModel::class.java.simpleName
    }

    lateinit var listener: IPurchasesListViewModel
    val isDay = data.isDarkMode

    // Adapter
    private val _adapter = MutableLiveData<RecyclerViewAdapterPurchases>(
        RecyclerViewAdapterPurchases(
            this
        )
    )
    val adapter: LiveData<RecyclerViewAdapterPurchases> = _adapter

    init {
        getAllPurchases()
    }

    fun getAllPurchases() {
        purchaseRepository.getAllPurchases()
            .subscribe(
                {
                    _adapter.value?.items = it.getGrouped()
                    _adapter.value?.notifyDataSetChanged()
                    Log.d(TAG, "Purchases size: ${it.size}")
                },
                { it.printStackTrace() }
            ).unSubscribeOnDestroy()
    }

    fun onClick(purchase: Purchase) = listener.onPurchaseClick(purchase)

    fun changeTheme() {
        isDay.value = !isDay.value!!
    }

}