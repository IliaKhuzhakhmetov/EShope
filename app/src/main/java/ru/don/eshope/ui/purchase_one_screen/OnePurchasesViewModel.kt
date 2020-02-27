package ru.don.eshope.ui.purchase_one_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.PurchaseAndItems
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.adapter.RecyclerViewAdapter

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

    // Adapter
    private val _adapter = MutableLiveData<RecyclerViewAdapter<Item, OnePurchasesViewModel>>()
    val adapter: LiveData<RecyclerViewAdapter<Item, OnePurchasesViewModel>> = _adapter

    // Purchase
    private val _purchase = MutableLiveData<PurchaseAndItems>()
    val purchase: LiveData<PurchaseAndItems> = _purchase

    // Items
    private val _items = MutableLiveData<MutableList<Item>>()
    val items: LiveData<MutableList<Item>> = _items

    init {
        _adapter.value = RecyclerViewAdapter(
            R.layout.item_purchase_item_without_delete,
            this
        )
        _adapter.value?.items = items
    }

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
            _purchase.value = it
            _items.value = it.items.toMutableList()

            _adapter.value?.notifyDataSetChanged()
        }, {
            it.printStackTrace()
        }).unSubscribeOnDestroy()
    }
}