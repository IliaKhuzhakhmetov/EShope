package ru.don.eshope.utils

import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

const val DAY_PATTERN = "dd MMMM yyyy"

fun Date.deleteHours(): Date {
    val sdf = SimpleDateFormat(DAY_PATTERN, Locale.getDefault())
    return sdf.parse(sdf.format(this)) ?: throw Exception("Error when try to parse: $this")
}

fun today(pattern: String = DAY_PATTERN): Long {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.parse(sdf.format(Calendar.getInstance().time))?.time ?: 0
}

fun String.getLongTimeByPattern(pattern: String = DAY_PATTERN): Long {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.parse(this)?.time ?: 0
}

fun String.getTimeByPattern(pattern: String = DAY_PATTERN): Long {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.parse(this)?.time ?: 0 + 80_000_000 // TODO need fix this Mistake
}

fun Long.getTimeByPattern(pattern: String = DAY_PATTERN): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}

fun Date.getTimeByPattern(pattern: String = DAY_PATTERN): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}

fun <T> MutableLiveData<T>.setDef(value: T) = apply {
    this.value = value
}

fun Completable.setThreads() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())


fun <T> Single<T>.setThreads() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
