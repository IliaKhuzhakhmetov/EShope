package ru.don.eshope.ui.purchases_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.R
import ru.don.eshope.data.DataProvider
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.adapter.HeaderItemDecorationPurchase
import ru.don.eshope.ui.adapter.RecyclerViewAdapter
import ru.don.eshope.utils.dip
import ru.don.eshope.utils.getTimeByPattern

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
    private val _adapter = MutableLiveData<RecyclerViewAdapter<Purchase, PurchasesViewModel>>()
    val adapter: LiveData<RecyclerViewAdapter<Purchase, PurchasesViewModel>> = _adapter

    // Purchases
    private val _purchases = MutableLiveData<MutableList<Purchase>>()
    val purchases: LiveData<MutableList<Purchase>> = _purchases

    // ItemDecorator
    private val _decorator = MutableLiveData<HeaderItemDecorationPurchase>()
    val decorator: LiveData<HeaderItemDecorationPurchase> = _decorator

    init {
        _adapter.value = RecyclerViewAdapter(
            R.layout.item_purchase,
            this
        )
        _adapter.value?.items = purchases

        getAllPurchases()
        reInitItemDecorator()
    }

    fun getAllPurchases() {
        purchaseRepository.getAllPurchases()
            .subscribe(
                {
                    _purchases.value = it.toMutableList().apply {
                        //sortBy { purchase -> purchase.id }
                        sortBy { purchase -> purchase.date }
                        reverse()
                    }
                    _adapter.value?.notifyDataSetChanged()
                    Log.d(TAG, "Purchases size: ${it.size}")
                },
                { it.printStackTrace() }
            ).unSubscribeOnDestroy()
    }

    // Need after user change THEME
    fun reInitItemDecorator() {
        _decorator.value = HeaderItemDecorationPurchase(
            context.dip(46),
            true,
            object : HeaderItemDecorationPurchase.SectionCallback {
                override fun isSection(position: Int): Boolean {
                    return position == 0 ||
                            _purchases.value?.get(position)?.date?.getTimeByPattern() !=
                            _purchases.value?.get(position - 1)?.date?.getTimeByPattern()
                }

                override fun getSectionHeader(position: Int): CharSequence {
                    return _purchases.value?.get(position)?.date?.getTimeByPattern() ?: ":("
                }

            })
    }

    fun onClick(purchase: Purchase) = listener.onPurchaseClick(purchase)

    fun changeTheme() {
        isDay.value = !isDay.value!!
    }

}