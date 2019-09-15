package ru.don.eshope.ui.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import ru.don.eshope.setDef

class DataProvider(context: Context) {

    companion object {
        private const val NAME = "Eshop"
        private const val DARK_MODE = "DarkModeSettings"
    }

    private val sp = context.getSharedPreferences(NAME, MODE_PRIVATE)

    fun getString(context: Context, @StringRes id: Int) = context.getString(id)

    /**
     * Сохранение строки в SP по имени <code>name</code> значение <code>value</code>
     * @param name Имя в SP, по этому имени в дальнейшем происходит и изъятие из SP
     * @param value Значение которое хотим сохранит
     */
    private fun saveString(name: String, value: String) = sp.edit().putString(name, value).apply()

    private fun saveBoolean(name: String, value: Boolean) =
        sp.edit().putBoolean(name, value).apply()

    /**
     * Удаление строки из SP по имени <code>name</code>
     * @param name Имя из SP, по этому имени в дальнейшем происходит и изъятие из SP
     */
    private fun deleteString(name: String) = sp.edit().remove(name).apply()

    val isDarkMode = object : MutableLiveData<Boolean>() {

        init {
            setDef(sp.getBoolean(DARK_MODE, true))
        }

        override fun setValue(value: Boolean) {
            super.setValue(value)

            saveBoolean(DARK_MODE, value)
        }
    }

}