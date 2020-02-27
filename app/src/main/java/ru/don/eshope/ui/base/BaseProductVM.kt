package ru.don.eshope.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roonyx.orcheya.ui.base.BaseViewModel
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

abstract class BaseProductVM :
    BaseViewModel() {

    protected val _date = MutableLiveData<String>(today())
    val date: LiveData<String> = _date

    val purchaseName = MutableLiveData<String>("")

    lateinit var listener: IPurchase

    open fun save() {

    }

    fun selectDate(time: Long) {
        _date.value = Date(time).getTimeByPattern()
    }

    fun changeTime() = listener.changeTime(date.value?.getTimeByPattern() ?: 0)

    fun clickAddItem() = listener.addItem()

    fun back() = listener.back()

}