package ru.don.eshope.repositories.interactors.purchase_intercator

import io.reactivex.Single
import ru.don.eshope.domain.entities.Purchase

interface IPurchaseInteractor {

    fun insert(purchase: Purchase)

    fun update(purchase: Purchase)

    fun delete(purchase: Purchase)

    fun deleteAllPurchases()

    fun getAllPurchases(): Single<List<Purchase>>

}