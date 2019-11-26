package ru.don.eshope.utils

import ru.don.eshope.database.entities.Item

fun ArrayList<Item>.getAmount(): Double =
    this.map {
        it.count * it.price
    }.sum()