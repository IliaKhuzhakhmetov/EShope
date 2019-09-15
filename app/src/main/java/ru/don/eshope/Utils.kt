package ru.don.eshope

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.setDef(value: T) = apply {
    this.value = value
}
