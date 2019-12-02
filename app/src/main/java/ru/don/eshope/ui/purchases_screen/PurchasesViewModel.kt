package ru.don.eshope.ui.purchases_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.data.DataProvider
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.adapter.HeaderItemDecorationPurchase
import ru.don.eshope.utils.dip

class PurchasesViewModel(
    data: DataProvider,
    private val purchaseRepository: PurchaseRepository,
    val context: Context
) :
    BaseViewModel() {

    companion object {
        val TAG = PurchasesViewModel::class.java.simpleName
    }

    val isDay = data.isDarkMode
    val purchases = MutableLiveData<ArrayList<Purchase>>(arrayListOf())
    lateinit var itemDecoration: HeaderItemDecorationPurchase

    init {
        getAllPurchases()
        reInitItemDecorator()
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

    // Need after user change THEME
    fun reInitItemDecorator() {
        itemDecoration = HeaderItemDecorationPurchase(
            context.dip(46),
            true,
            object : HeaderItemDecorationPurchase.SectionCallback {
                override fun isSection(position: Int): Boolean {
                    return position == 0 ||
                            purchases.value?.get(position)?.date !=
                            purchases.value?.get(position - 1)?.date
                }

                override fun getSectionHeader(position: Int): CharSequence {
                    return purchases.value?.get(position)?.date ?: ":("
                }

            })
    }

}