package ru.don.eshope.data

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData

class DataProvider(context: Context) : BaseDataProvider(context, NAME) {

    companion object {
        private const val NAME = "Eshop"
        private const val DARK_MODE = "DarkModeSettings"
    }

    val isDarkMode = object : MutableLiveData<Boolean>() {

        init {
            postValue(getValue(DARK_MODE, true))
            if (value == true) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        override fun setValue(value: Boolean) {
            super.setValue(value)
            saveValue(DARK_MODE, value)

            if (value) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}