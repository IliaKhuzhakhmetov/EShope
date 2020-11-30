package ru.don.eshope.ui.add_purchase_screen

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.don.eshope.ui.base.BaseViewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.adapter.RecyclerViewAdapter
import ru.don.eshope.utils.getAmount

class PurchaseListViewModel(private val purchaseRepository: PurchaseRepository) : BaseViewModel() {

    // Adapter
    private val _adapter = MutableLiveData<RecyclerViewAdapter<Item, PurchaseListViewModel>>()
    val adapter: LiveData<RecyclerViewAdapter<Item, PurchaseListViewModel>> = _adapter

    // Name and Price Error
    private val _validateErrorMsg = MutableLiveData<Int>()
    val validateErrorMsg: LiveData<Int> = _validateErrorMsg

    // Edit item
    private val _editItem = MutableLiveData<Item>()
    val edititem: LiveData<Item> = _editItem

    // Delete item
    private val _deleteItem = MutableLiveData<Item>()
    val deleteItem: LiveData<Item> = _deleteItem

    // Amount
    private val _amount = MutableLiveData<Double>(0.0)
    val amount: LiveData<Double> = _amount

    private val _items = MutableLiveData<MutableList<Item>>(mutableListOf())
    val items: LiveData<MutableList<Item>> = _items

    init {
        _adapter.value = RecyclerViewAdapter(
            R.layout.item_purchase_item,
            this
        )
        _adapter.value?.items = items
    }

    companion object {
        val TAG = PurchaseListViewModel::class.java.simpleName
    }

    fun edit(item: Item): Boolean {
        _editItem.value = item
        return false
    }

    fun delete(item: Item) {
        _deleteItem.value = item
    }

    fun validateNamePrice(name: String?, price: String?, item: Item? = null): Boolean {
        return if (validateName(name)) {
            if (validatePrice(price)) {
                if (item == null)
                    addNewItem(name, price?.toDouble())
                else {
                    item.name = name!!
                    item.price = price!!.toDouble()
                }
                _amount.value = _items.value?.getAmount()
                adapter.value?.notifyDataSetChanged()
                true
            } else false
        } else false
    }

    private fun validateName(name: String?): Boolean {
        return when (name ?: "") {
            "" -> {
                _validateErrorMsg.value = R.string.name_emty_error
                false
            }
            else -> true
        }
    }

    private fun validatePrice(price: String?): Boolean {
        return when (price) {
            "" -> {
                _validateErrorMsg.value = R.string.price_empty
                false
            }
            "." -> {
                _validateErrorMsg.value = R.string.er
                false
            }
            null -> {
                _validateErrorMsg.value = R.string.price_null
                false
            }
            else -> true
        }
    }

    private fun addNewItem(name: String?, price: Double?) {
        _items.value?.add(
            Item(null, name!!, 1, price!!, null)
        )
        Log.d(AddPurchasesViewModel.TAG, "Items size: ${_items.value?.size}")
    }

    fun deleteItem(item: Item) {
        _items.value?.remove(item)
        _adapter.value?.notifyDataSetChanged()
        _amount.value = _items.value?.getAmount()
    }

    fun init(id: Int) {
        purchaseRepository.getById(id).subscribe(
            {
                _items.value = it.items.toMutableList()
                _adapter.value?.notifyDataSetChanged()
                _amount.value = _items.value?.getAmount()
            }, {
                it.printStackTrace()
            }).unSubscribeOnDestroy()
    }
}