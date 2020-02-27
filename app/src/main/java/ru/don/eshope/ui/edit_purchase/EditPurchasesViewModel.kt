package ru.don.eshope.ui.edit_purchase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.entities.PurchaseAndItems
import ru.don.eshope.database.repos.ItemRepository
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.base.BaseProductVM
import ru.don.eshope.utils.getAmount
import ru.don.eshope.utils.today


class EditPurchasesViewModel(
    private val itemRepository: ItemRepository,
    private val purchaseRepository: PurchaseRepository
) :
    BaseProductVM() {

    companion object {
        val TAG = EditPurchasesViewModel::class.java.simpleName
    }

    private var purchase = MutableLiveData<PurchaseAndItems>()

    fun save(items: List<Item>) {
        if (purchaseName.value?.isEmpty() == true) {
            listener.emptyName();
            return
        }

        if (items.isEmpty()) {
            listener.emptyBasket()
            return
        }

        purchaseRepository.update(
            Purchase(
                purchase.value?.id ?: -1,
                purchaseName.value ?: "",
                date.value ?: today(),
                items.getAmount()
            )
        ).invokeOnCompletion { saveItems(items) }

    }

    private fun saveItems(items: List<Item>) {
        itemRepository.deleteByPurchaseId(purchase.value?.id ?: -1).invokeOnCompletion {
            // ReAdd items
            purchase.value?.id?.let { id ->
                items.forEach { item ->
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
                _date.value = it.date
                purchaseName.value = it.name
            }, {
                it.printStackTrace()
            }).unSubscribeOnDestroy()
    }

}