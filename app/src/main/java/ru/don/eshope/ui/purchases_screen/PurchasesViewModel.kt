package ru.don.eshope.ui.purchases_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.DAY_PATTERN
import ru.don.eshope.data.DataProvider
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.getTimeByPattern
import ru.don.eshope.setDef
import java.util.*

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
            Purchase(null, "Kek", Calendar.getInstance().time.getTimeByPattern(), 23.0)
            , {
                getAllPurchases()
            },
            {
                it.printStackTrace()
            })
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