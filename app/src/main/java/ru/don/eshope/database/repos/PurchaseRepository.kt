package ru.don.eshope.database.repos

import kotlinx.coroutines.launch
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.database.entities.PurchaseDao
import ru.don.eshope.database.repos.base.BaseRepo
import ru.don.eshope.utils.setThreads

class PurchaseRepository(private val purchaseDao: PurchaseDao) : BaseRepo() {

    fun insert(
        purchase: Purchase,
        success: (id: Int) -> Unit
    ) = launch {
        makeOnIoThread {
            success(purchaseDao.insert(purchase).toInt())
        }
    }

    fun update(purchase: Purchase) = launch {
        makeOnIoThread {
            purchaseDao.update(purchase)
        }
    }

    fun delete(purchase: Purchase) = launch {
        makeOnIoThread {
            purchaseDao.delete(purchase)
        }
    }

    fun deleteAllPurchases() = launch {
        makeOnIoThread {
            purchaseDao.deleteAllPurchases()
        }
    }

    fun getById(id: Int) =
        purchaseDao.getById(id).setThreads()

    fun getAllPurchases() =
        purchaseDao.getAllPurchases().setThreads()
}

