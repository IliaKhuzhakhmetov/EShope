package ru.don.eshope.ui.add_purchase_screen

import android.util.Log
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.database.entities.Item

interface AddPurchasesListListener {
    fun onDelete(item: Item)
}

class AddPurchasesListViewModel : BaseViewModel() {

    companion object {
        val TAG = AddPurchasesListViewModel::class.java.simpleName
    }

    lateinit var listener: AddPurchasesListListener

    fun delete(item: Item) {
        listener.onDelete(item)
        Log.d(TAG, "Item ${item.name} deleted")
    }
}