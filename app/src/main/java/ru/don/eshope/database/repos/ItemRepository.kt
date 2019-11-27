package ru.don.eshope.database.repos

import kotlinx.coroutines.launch
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.ItemDao
import ru.don.eshope.database.repos.base.BaseRepo
import ru.don.eshope.utils.setThreads

class ItemRepository(private val itemDao: ItemDao) : BaseRepo() {

    fun insert(item: Item, success: (id: Int) -> Unit) = launch {
        makeOnIoThread {
            success(itemDao.insert(item).toInt())
        }
    }

    fun update(item: Item) = launch {
        makeOnIoThread {
            itemDao.update(item)
        }
    }

    fun delete(item: Item) = launch {
        makeOnIoThread {
            itemDao.delete(item)
        }
    }

    fun deleteByPurchaseId(purchaseId: Int) = launch {
        makeOnIoThread {
            itemDao.deleteByPurchaseId(purchaseId).subscribe()
        }
    }

    fun getById(id: Int) =
        itemDao.getById(id).setThreads()

    fun getAllPurchases() =
        itemDao.getAllItems().setThreads()
}

