package ru.don.eshope.repositories.interactors.purchase_intercator

import io.reactivex.Single
import ru.don.eshope.domain.entities.Purchase
import ru.don.eshope.repositories.purchase_repo.IPurchaseRepository

class PurchaseInteractor(private val iPurchaseRepository: IPurchaseRepository) :
    IPurchaseInteractor {

    override fun insert(purchase: Purchase) {
        iPurchaseRepository.insert(purchase)
    }

    override fun update(purchase: Purchase) {
        iPurchaseRepository.update(purchase)
    }

    override fun delete(purchase: Purchase) {
        iPurchaseRepository.update(purchase)
    }

    override fun deleteAllPurchases() {
        iPurchaseRepository.deleteAllPurchases()
    }

    override fun getAllPurchases(): Single<List<Purchase>> =
        iPurchaseRepository.getAllPurchases()

}