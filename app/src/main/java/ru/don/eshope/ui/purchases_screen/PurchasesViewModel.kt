package ru.don.eshope.ui.purchases_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.data.DataProvider
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.repos.PurchaseRepository

class PurchasesViewModel(data: DataProvider, private val purchaseRepository: PurchaseRepository) :
    BaseViewModel() {

    companion object {
        val TAG = PurchasesViewModel::class.java.simpleName
    }

    val isDay = data.isDarkMode
    val purchases = MutableLiveData<ArrayList<Purchase>>(arrayListOf())

    init {
        getAllPurchases()
    }

    fun changeTheme() {
        isDay.value = !isDay.value!!
    }

    fun getAllPurchases() {
        purchaseRepository.getAllPurchases()
            .subscribe(
                {
                    purchases.value = ArrayList(it).apply {
                        sortBy { purchase -> purchase.id }
                        sortBy { purchase -> purchase.date }
                        reverse()
                    }
                    Log.d(TAG, "Purchases size: ${it.size}")
                },
                { it.printStackTrace() }
            ).unSubscribeOnDestroy()
    }

}