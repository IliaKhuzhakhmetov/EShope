package ru.don.eshope.utils

import android.util.Log
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.models.DateAmount
import kotlin.math.log

fun Collection<Item>.getAmount(): Double =
    map {
        it.count * it.price
    }.sum()

fun Collection<Purchase>.getGrouped(): List<Any> {
    val map: HashMap<String, MutableList<Purchase>> = HashMap()
    val list: MutableList<Any> = mutableListOf()

    toMutableList().apply {
        //sortBy { purchase -> purchase.id }
        sortBy { purchase ->
            purchase.date
            Log.d("Tag - sort", "${purchase.date} - ${purchase.name}")
        }
        //reverse()
    }.forEach {
        if (map[it.date.getTimeByPattern()] == null) map[it.date.getTimeByPattern()] =
            mutableListOf()
        map[it.date.getTimeByPattern()]?.add(it)
    }

    map.values.toList().sortedBy { mutableList ->
        mutableList.first().date
    }.reversed().forEach { mutableList ->
        list.add(DateAmount(mutableList.first().date, mutableList.sumByDouble {
            it.amount
        }))
        mutableList.forEach {
            list.add(it)
        }
    }

    return list
}