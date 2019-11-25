package ru.don.eshope.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.annotation.StringRes

/**
 * A base class helper that helps to keep values in a SharedPrefrenches
 * @param context [Context]
 * @param name Base name for [SharedPreferences] class
 */
abstract class BaseDataProvider(context: Context, name: String) {

    protected val sp: SharedPreferences = context.getSharedPreferences(name, MODE_PRIVATE)

    fun getString(context: Context, @StringRes id: Int) = context.getString(id)

    protected inline fun <reified T> saveValue(name: String, def: T) {
        when (def) {
            is String -> sp.edit().putString(name, def).apply()
            is Boolean -> sp.edit().putBoolean(name, def).apply()
            is Int -> sp.edit().putInt(name, def).apply()
            else -> throw Exception("Can't set ${T::class.java.simpleName} from SP")
        }
    }

    protected inline fun <reified T> getValue(name: String, def: T): T {
        return when (def) {
            is String -> sp.getString(name, def) as T
            is Boolean -> sp.getBoolean(name, def) as T
            is Int -> sp.getInt(name, def) as T
            else -> throw Exception("Can't get ${T::class.java.simpleName} from SP")
        }
    }

    protected fun deleteValue(name: String) = sp.edit().remove(name).apply()

}