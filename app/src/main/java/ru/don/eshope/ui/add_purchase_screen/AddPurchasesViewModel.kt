package ru.don.eshope.ui.add_purchase_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.repos.ItemRepository
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.utils.getAmount
import ru.don.eshope.utils.today

interface IAddPurchase {
    fun addItem()
    fun back()
    fun emptyBasket()
    fun emptyName()
}

class AddPurchasesViewModel(
    private val itemRepository: ItemRepository,
    private val purchaseRepository: PurchaseRepository
) :
    BaseViewModel() {

    companion object {
        val TAG = AddPurchasesViewModel::class.java.simpleName
    }

    val amount = MutableLiveData<Double>(0.0)
    val purchaseName = MutableLiveData<String>("")
    val items = MutableLiveData<ArrayList<Item>>(arrayListOf())
    lateinit var listener: IAddPurchase

    fun clickAddItem() = listener.addItem()

    fun back() = listener.back()

    fun save() {

        if (purchaseName.value?.isEmpty() == true) {
            listener.emptyName()
            return
        }

        if (items.value?.isEmpty() == true) {
            listener.emptyBasket()
            return
        }

        purchaseRepository.insert(
            Purchase(
                null,
                purchaseName.value ?: "No name",
                today(),
                items.value?.getAmount() ?: 0.0
            )
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
        amount.value = amount.value?.plus(price!!)
        Log.d(TAG, "Items size: ${items.value?.size}")
    }

    fun deleteItem(item: Item) {
        items.value?.remove(item)
        amount.value = amount.value?.minus(item.price * item.count)
    }

}