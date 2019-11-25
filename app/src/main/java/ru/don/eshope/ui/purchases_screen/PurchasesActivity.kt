package ru.don.eshope.ui.purchases_screen

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roonyx.orcheya.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_purchases.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.databinding.ActivityPurchasesBinding
import ru.don.eshope.ui.adapter.RecyclerViewAdapter

class PurchasesActivity : BaseActivity<ActivityPurchasesBinding>() {

    override val layoutId = R.layout.activity_purchases
    private val listVM: PurchasesListViewModel by viewModel()
    private val vm: PurchasesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = vm

        initPurchasesRv()
    }

    private fun initPurchasesRv() {
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = RecyclerViewAdapter<Purchase, PurchasesListViewModel>(
            R.layout.item_purchase,
            listVM
        )

        adapter.items = vm.purchases
        rv.adapter = adapter

        vm.purchases.observe(
            {
                lifecycle
            }, {
                adapter.notifyDataSetChanged()
            }
        )
    }

}