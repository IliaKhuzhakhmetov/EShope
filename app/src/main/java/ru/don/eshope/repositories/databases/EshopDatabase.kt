package ru.don.eshope.repositories.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.don.eshope.domain.entities.Purchase
import ru.don.eshope.domain.entities.PurchaseDao

@Database(entities = [Purchase::class], version = 1)
abstract class EshopDatabase: RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

}