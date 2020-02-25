package ru.don.eshope.ui.edit_purchase

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.entities.PurchaseAndItems
import ru.don.eshope.database.repos.ItemRepository
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.base.BaseProductVM
import ru.don.eshope.utils.getTimeByPattern
import ru.don.eshope.utils.today
import java.util.*
import kotlin.collections.ArrayList



class EditPurchasesViewModel(
    override val context: Context,
    private val itemRepository: ItemRepository,
    private val purchaseRepository: PurchaseRepository
) :
    BaseProductVM(context) {

    companion object {
        val TAG = EditPurchasesViewModel::class.java.simpleName
    }

    private var purchase = MutableLiveData<PurchaseAndItems>()

    override fun save() {
        if (purchaseName.value?.isEmpty() == true) {
            listener.emptyName();return
        }

        if (items.value?.isEmpty() == true) {
            listener.emptyBasket(); return
        }

        purchaseRepository.update(
            Purchase(
                purchase.value?.id ?: -1,
                purchaseName.value ?: "",
                date.value ?: today(),
                amount.value ?: 0.0
            )
        ).invokeOnCompletion { saveItems() }

    }

    private fun saveItems() {
        itemRepository.deleteByPurchaseId(purchase.value?.id ?: -1).invokeOnCompletion {
            // ReAdd items
            purchase.value?.id?.let { id ->
                items.value?.forEach { item ->
                    itemRepository.insert(
                        item.apply {
                            this.id = null
                            purchaseId = id
                        }
                    ) {
                        Log.d(TAG, "Item $it was create")
                    }
                }
                listener.back()
            }
        }
    }

    fun init(id: Int) {
        purchaseRepository.getById(id).subscribe(
            {
                purchase.value = it
                _amount.value = it.amount
                _date.value = it.date
                _items.value = ArrayList(it.items)
                purchaseName.value = it.name
            }, {
                it.printStackTrace()
            }).unSubscribeOnDestroy()
    }

}