package ru.don.eshope.ui.purchase_one_screen

import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.PurchaseAndItems
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.utils.getTimeByPattern
import java.util.*
import kotlin.collections.ArrayList

interface IOnePurchasesViewModel {
    fun back()
    fun deleteDialog()
    fun edit(purchase: PurchaseAndItems)
}

class OnePurchasesViewModel(
    private val purchaseRepository: PurchaseRepository
) :
    BaseViewModel() {

    companion object {
        val TAG = OnePurchasesViewModel::class.java.simpleName
    }

    lateinit var listener: IOnePurchasesViewModel
    val amount = MutableLiveData<Double>(0.0)
    val purchaseName = MutableLiveData<String>("")
    val items = MutableLiveData<ArrayList<Item>>(arrayListOf())
    val date = MutableLiveData<String>()
    private val purchase = MutableLiveData<PurchaseAndItems>()

    fun edit() = listener.edit(purchase.value!!)

    fun back() = listener.back()

    fun deleteDialog() = listener.deleteDialog()

    fun deletePurchase() {
        purchaseRepository.deleteById(purchase.value?.id ?: -1, {
            back()
        }, {
            it.printStackTrace()
        })
    }

    fun getItemsByPurchaseId(id: Int) {
        purchaseRepository.getById(id).subscribe({
            date.value = it.date
            purchase.value = it
            purchaseName.value = it.name
            amount.value = it.amount
            items.value = ArrayList(it.items)
        }, {
            it.printStackTrace()
        }).unSubscribeOnDestroy()
    }
}