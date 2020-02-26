package ru.don.eshope.ui.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Item
import ru.don.eshope.ui.add_purchase_screen.AddPurchasesViewModel
import ru.don.eshope.utils.getTimeByPattern
import ru.don.eshope.utils.today
import java.util.*

interface IPurchase {
    fun addItem()
    fun back()
    fun emptyBasket()
    fun emptyName()
    fun changeTime(time: Long)
}

abstract class BaseProductVM(open val context: Context) : BaseViewModel() {

    // Name and Price Error
    protected val _validateErrorMsg = MutableLiveData<String>()
    val validateErrorMsg: LiveData<String> = _validateErrorMsg

    protected val _date = MutableLiveData<String>(today())
    val date: LiveData<String> = _date

    protected val _amount = MutableLiveData<Double>(0.0)
    val amount: LiveData<Double> = _amount

    val purchaseName = MutableLiveData<String>("")

    protected val _items = MutableLiveData<ArrayList<Item>>(arrayListOf())
    val items: LiveData<ArrayList<Item>> = _items

    lateinit var listener: IPurchase

    open fun save() {

    }

    fun deleteItem(item: Item) {
        _items.value?.remove(item)
        _amount.value = _amount.value?.minus(item.price * item.count)
    }

    fun selectDate(time: Long) {
        _date.value = Date(time).getTimeByPattern()
    }

    fun changeTime() = listener.changeTime(date.value?.getTimeByPattern() ?: 0)

    fun clickAddItem() = listener.addItem()

    fun back() = listener.back()

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
                true
            } else false
        } else false
    }

    private fun validateName(name: String?): Boolean {
        return when (name ?: "") {
            "" -> {
                _validateErrorMsg.value = context.getString(R.string.name_emty_error)
                false
            }
            else -> true
        }
    }

    private fun validatePrice(price: String?): Boolean {
        return when (price) {
            "" -> {
                _validateErrorMsg.value = context.getString(R.string.price_empty)
                false
            }
            "." -> {
                _validateErrorMsg.value = context.getString(R.string.er)
                false
            }
            null -> {
                _validateErrorMsg.value = context.getString(R.string.price_null)
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


}