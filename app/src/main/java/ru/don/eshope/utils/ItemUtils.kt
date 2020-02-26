package ru.don.eshope.utils

import ru.don.eshope.database.entities.Item

fun Collection<Item>.getAmount(): Double =
    this.map {
        it.count * it.price
    }.sum()