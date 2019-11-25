package ru.don.eshope.utils

import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

const val DAY_PATTERN = "dd MMMM yyyy"

fun today() = Calendar.getInstance().time.getTimeByPattern()

fun Date.getTimeByPattern(pattern: String = DAY_PATTERN): String {
    val sdf = SimpleDateFormat(pattern, Locale.US)
    return sdf.format(this)
}

fun <T> MutableLiveData<T>.setDef(value: T) = apply {
    this.value = value
}

fun Completable.setThreads() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())


fun <T> Single<T>.setThreads() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
