package ru.don.eshope.ui.add_purchase_screen

import android.content.Context
import android.util.Log
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.repos.ItemRepository
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.base.BaseProductVM
import ru.don.eshope.utils.getAmount
import ru.don.eshope.utils.today

class AddPurchasesViewModel(
    override val context: Context,
    private val itemRepository: ItemRepository,
    private val purchaseRepository: PurchaseRepository
) :
    BaseProductVM(context) {

    companion object {
        val TAG = AddPurchasesViewModel::class.java.simpleName
    }

    fun save(items: List<Item>) {
        if (purchaseName.value?.isEmpty() == true) {
            listener.emptyName()
            return
        }

        if (items.isEmpty()) {
            listener.emptyBasket()
            return
        }

        purchaseRepository.insert(
            Purchase(
                null,
                purchaseName.value ?: "No name",
                date.value ?: today(),
                items.getAmount()
            )
        ) { id ->
            items.forEach { item ->
                itemRepository.insert(
                    item.apply { purchaseId = id }
                ) {
                    Log.d(TAG, "Item $it was create")
                }
            }
            listener.back()
        }
    }

}