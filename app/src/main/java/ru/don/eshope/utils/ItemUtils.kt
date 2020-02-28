package ru.don.eshope.utils

import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.models.DateAmount

fun Collection<Item>.getAmount(): Double =
    this.map {
        it.count * it.price
    }.sum()

fun Collection<Purchase>.getGrouped(): List<Any> {
    val map: HashMap<String, MutableList<Purchase>> = HashMap()
    val list: MutableList<Any> = mutableListOf()

    this.toMutableList().apply {
        //sortBy { purchase -> purchase.id }
        sortBy { purchase -> purchase.date }
        reverse()
    }.forEach {
        if (map[it.date.getTimeByPattern()] == null) map[it.date.getTimeByPattern()] =
            mutableListOf()
        map[it.date.getTimeByPattern()]?.add(it)
    }

    map.values.forEach { mutableList ->
        list.add(DateAmount(mutableList.first().date, mutableList.sumByDouble {
            it.amount
        }))
        mutableList.forEach {
            list.add(it)
        }
    }

    return list
}