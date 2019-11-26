package ru.don.eshope.ui.add_purchase_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.repos.ItemRepository
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.utils.today

interface IAddPurchase {
    fun addItem()
    fun back()
}

class AddPurchasesViewModel(
    val itemRepository: ItemRepository,
    val purchaseRepository: PurchaseRepository
) :
    BaseViewModel() {

    companion object {
        val TAG = AddPurchasesViewModel::class.java.simpleName
    }

    val purchaseName = MutableLiveData<String>("")
    val items = MutableLiveData<MutableList<Item>>(mutableListOf())
    lateinit var listener: IAddPurchase

    fun clickAddItem() {
        listener.addItem()
    }

    fun save() {
        var amount = 0.0
        items.value?.forEach {
            amount += it.count * it.price
        }

        purchaseRepository.insert(
            Purchase(null, purchaseName.value ?: "No name", today(), amount)
        ) { id ->
            items.value?.forEach { item ->
                itemRepository.insert(
                    item.apply { purchaseId = id }
                ) {
                    Log.d(TAG, "Item $it was create")
                }
            }
            listener.back()
        }
    }

    fun addNewItem(name: String?, price: Double?) {
        items.value?.add(
            Item(null, name!!, 1, price!!, null)
        )
        Log.d(TAG, "Items size: ${items.value?.size}")
    }


}