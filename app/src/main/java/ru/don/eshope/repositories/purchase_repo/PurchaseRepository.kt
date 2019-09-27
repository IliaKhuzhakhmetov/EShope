package ru.don.eshope.repositories.purchase_repo

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import ru.don.eshope.domain.entities.Purchase
import ru.don.eshope.domain.entities.PurchaseDao
import ru.don.eshope.setThreads

class PurchaseRepository(private val purchaseDao: PurchaseDao) : IPurchaseRepository {

    companion object {
        val TAG = PurchaseRepository::class.java.simpleName
    }

    override fun insert(purchase: Purchase) {
        purchaseDao.insert(purchase)
            .setThreads()
            .subscribe(
                { Log.d(TAG, "Insert Success") },
                { Log.d(TAG, "Insert Error") }
            )
    }

    override fun update(purchase: Purchase) {
        purchaseDao.update(purchase)
            .setThreads()
            .subscribe(
                { Log.d(TAG, "Update Success") },
                { Log.d(TAG, "Update Error") }
            )
    }

    override fun delete(purchase: Purchase) {
        purchaseDao.delete(purchase)
            .setThreads()
            .subscribe(
                { Log.d(TAG, "Delete Success") },
                { Log.d(TAG, "Delete Error") }
            )
    }

    override fun deleteAllPurchases() {
        Completable.fromAction { purchaseDao.deleteAllPurchases() }
            .setThreads()
            .subscribe(
                { Log.d(TAG, "Delete all Success") },
                { Log.d(TAG, "Delete all Error") }
            )

    }

    override fun getAllPurchases(): Single<List<Purchase>> = purchaseDao.getAllPurchases()

}