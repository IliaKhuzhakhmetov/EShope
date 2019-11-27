package ru.don.eshope.ui.edit_purchase

import android.util.Log
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.database.entities.Item

interface EditPurchasesListListener {
    fun onDelete(item: Item)
}

class EditPurchasesListViewModel : BaseViewModel() {

    companion object {
        val TAG = EditPurchasesListViewModel::class.java.simpleName
    }

    lateinit var listener: EditPurchasesListListener

    fun delete(item: Item) {
        listener.onDelete(item)
        Log.d(TAG, "Item ${item.name} deleted")
    }
}