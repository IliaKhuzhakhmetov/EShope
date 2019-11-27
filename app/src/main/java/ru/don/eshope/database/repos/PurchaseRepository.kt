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
            purchaseDao.updateName(purchase.name, purchase.id)
            purchaseDao.updateDate(purchase.date, purchase.id)
            purchaseDao.updateAmount(purchase.amount, purchase.id)
        }
    }

    fun delete(purchase: Purchase) = launch {
        makeOnIoThread {
            purchaseDao.delete(purchase)
        }
    }

    fun deleteById(id: Int, success: () -> Unit, error: (throwable: Throwable) -> Unit) = launch {
        makeOnIoThread {
            purchaseDao.deleteById(id).subscribe({
                success()
            }, {
                error(it)
            })
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

