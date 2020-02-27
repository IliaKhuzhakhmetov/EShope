package ru.don.eshope.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.ItemDao
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.entities.PurchaseDao

@Database(entities = [Purchase::class, Item::class], version = 2)
abstract class EshopDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao
    abstract fun itemDao(): ItemDao

}