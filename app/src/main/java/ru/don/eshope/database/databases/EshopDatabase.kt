package ru.don.eshope.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.entities.PurchaseDao

@Database(entities = [Purchase::class], version = 1)
abstract class EshopDatabase: RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

}