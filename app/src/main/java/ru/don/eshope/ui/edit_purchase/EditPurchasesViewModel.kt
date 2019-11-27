package ru.don.eshope.ui.edit_purchase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.entities.PurchaseAndItems
import ru.don.eshope.database.repos.ItemRepository
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.utils.getTimeByPattern
import ru.don.eshope.utils.today
import java.util.*
import kotlin.collections.ArrayList

interface IEditPurchase {
    fun addItem()
    fun changeTime(time: Long)
    fun back()
    fun emptyBasket()
    fun emptyName()
}

class EditPurchasesViewModel(
    private val itemRepository: ItemRepository,
    private val purchaseRepository: PurchaseRepository
) :
    BaseViewModel() {

    companion object {
        val TAG = EditPurchasesViewModel::class.java.simpleName
    }

    val date = MutableLiveData<String>("")
    val amount = MutableLiveData<Double>(0.0)
    val purchaseName = MutableLiveData<String>("")
    val items = MutableLiveData<ArrayList<Item>>(arrayListOf())
    private var purchase = MutableLiveData<PurchaseAndItems>()
    lateinit var listener: IEditPurchase

    fun clickAddItem() = listener.addItem()

    fun back() = listener.back()

    fun changeTime() = listener.changeTime(date.value?.getTimeByPattern() ?: 0)

    fun save() {
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
                amount.value = it.amount
                date.value = it.date
                items.value = ArrayList(it.items)
                purchaseName.value = it.name
            }, {
                it.printStackTrace()
            }).unSubscribeOnDestroy()
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

    fun selectDate(time: Long) {
        this.date.value = Date(time).getTimeByPattern()
    }

}