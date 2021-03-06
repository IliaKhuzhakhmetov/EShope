package ru.don.eshope.ui.purchases_screen

import android.content.Intent
import android.os.Bundle
import ru.don.eshope.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_purchases.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.databinding.ActivityPurchasesBinding
import ru.don.eshope.ui.add_purchase_screen.AddPurchasesActivity
import ru.don.eshope.ui.purchase_one_screen.OnePurchasesActivity
import ru.don.eshope.ui.purchase_one_screen.OnePurchasesActivity.Companion.ID

class PurchasesActivity : BaseActivity<ActivityPurchasesBinding>(), IPurchasesListViewModel {

    override val layoutId = R.layout.activity_purchases
    private val vm: PurchasesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = vm

        fab.setOnClickListener {
            val intent = Intent(this, AddPurchasesActivity::class.java)
            startActivity(intent)
        }

        initPurchasesRv()
    }

    override fun onResume() {
        super.onResume()
        vm.getAllPurchases()
    }

    private fun initPurchasesRv() {
        vm.listener = this
    }

    override fun onPurchaseClick(purchase: Purchase) {
        val intent = Intent(this, OnePurchasesActivity::class.java).apply {
            putExtra(ID, purchase.id)
        }
        startActivity(intent)
    }

}