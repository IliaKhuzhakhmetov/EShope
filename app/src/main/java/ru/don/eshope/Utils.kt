package ru.don.eshope

import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> MutableLiveData<T>.setDef(value: T) = apply {
    this.value = value
}

fun Completable.setThreads() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())


fun <T> Single<T>.setThreads() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
