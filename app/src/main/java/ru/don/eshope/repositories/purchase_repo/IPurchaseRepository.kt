package ru.don.eshope.repositories.purchase_repo

import io.reactivex.Single
import ru.don.eshope.domain.entities.Purchase

interface IPurchaseRepository {

    fun insert(purchase: Purchase)

    fun update(purchase: Purchase)

    fun delete(purchase: Purchase)

    fun deleteAllPurchases()

    fun getAllPurchases(): Single<List<Purchase>>

}