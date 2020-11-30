package ru.don.eshope.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.don.eshope.utils.today

interface IPurchase {
    fun addItem()
    fun back()
    fun emptyBasket()
    fun emptyName()
    fun changeTime(time: Long)
}

abstract class BaseProductVM :
    BaseViewModel() {

    protected val _date = MutableLiveData<Long>(today())
    val date: LiveData<Long> = _date

    val purchaseName = MutableLiveData<String>("")

    lateinit var listener: IPurchase

    open fun save() {

    }

    fun selectDate(time: Long) {
        _date.value = time
    }

    fun changeTime() = listener.changeTime(date.value ?: 0)

    fun clickAddItem() = listener.addItem()

    fun back() = listener.back()

}