package ru.don.eshope.ui.purchases_screen

import androidx.appcompat.app.AppCompatDelegate
import com.roonyx.orcheya.ui.base.BaseViewModel
import ru.don.eshope.ui.data.DataProvider

class PurchasesViewModel(data: DataProvider) : BaseViewModel() {

    val isDay = data.isDarkMode

    fun changeTheme() {
        if (isDay.value == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            isDay.value = false
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            isDay.value = true
        }
    }


}