package ru.don.eshope.ui.add_purchase_screen

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.repos.PurchaseRepository
import ru.don.eshope.ui.adapter.RecyclerViewAdapter

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

    private val _items = MutableLiveData<MutableList<Item>>(arrayListOf())
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

    fun edit(view: View, item: Item): Boolean {
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
                    updateAmount()
                }
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

    private fun updateAmount() {
        _amount.value = _items.value?.sumByDouble {
            it.price
        }
    }

    private fun addNewItem(name: String?, price: Double?) {
        _items.value?.add(
            Item(null, name!!, 1, price!!, null)
        )
        updateAmount()
        Log.d(AddPurchasesViewModel.TAG, "Items size: ${_items.value?.size}")
    }

    fun deleteItem(item: Item) {
        _items.value?.remove(item)
        _amount.value = _amount.value?.minus(item.price * item.count)
        _adapter.value?.notifyDataSetChanged()
    }

    fun init(id: Int) {
        purchaseRepository.getById(id).subscribe(
            {
                _items.value = it.items.toMutableList()
                _adapter.value?.notifyDataSetChanged()
            }, {
                it.printStackTrace()
            }).unSubscribeOnDestroy()
    }
}