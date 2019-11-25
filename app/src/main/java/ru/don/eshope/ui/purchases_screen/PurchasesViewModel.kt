package ru.don.eshope.ui.purchases_screen

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.data.DataProvider
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.setDef

class PurchasesViewModel(data: DataProvider, private val purchaseRepository: PurchaseRepository) :
    BaseViewModel() {

    companion object {
        val TAG = PurchasesViewModel::class.java.simpleName
    }

    val isDay = data.isDarkMode
    val purchases = MutableLiveData<List<Purchase>>().setDef(listOf())

    init {
        getAllPurchases()
    }

    fun changeTheme() {
        isDay.value = !isDay.value!!
    }

    fun addPurchase() {
        purchaseRepository.insert(
            Purchase(null, "Kek", "lol", 23.0)
        ).subscribe({
            Log.d(TAG, "success")
        }
            , {
                it.printStackTrace()
            }).unSubscribeOnDestroy()
        getAllPurchases()
    }


    private fun getAllPurchases() {
        purchaseRepository.getAllPurchases()
            .subscribe(
                {
                    purchases.value = it
                    Log.d(TAG, "Purchases size: ${it.size}")
                },
                { it.printStackTrace() }
            ).unSubscribeOnDestroy()
    }

}