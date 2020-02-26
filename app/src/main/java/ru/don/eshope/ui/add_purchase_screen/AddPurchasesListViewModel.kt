package ru.don.eshope.ui.add_purchase_screen

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.database.entities.Item

class AddPurchasesListViewModel : BaseViewModel() {

    // Edit item
    private val _editItem = MutableLiveData<Item>()
    val edititem: LiveData<Item> = _editItem

    // Delete item
    private val _deleteItem = MutableLiveData<Item>()
    val deleteItem: LiveData<Item> = _deleteItem

    companion object {
        val TAG = AddPurchasesListViewModel::class.java.simpleName
    }

    fun edit(view: View, item: Item): Boolean {
        _editItem.value = item
        return false
    }

    fun delete(item: Item) {
        _deleteItem.value = item
    }
}