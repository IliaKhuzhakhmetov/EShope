package ru.don.eshope.ui.purchases_screen

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roonyx.orcheya.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_purchases.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.databinding.ActivityPurchasesBinding
import ru.don.eshope.ui.adapter.HeaderItemDecoration
import ru.don.eshope.ui.adapter.RecyclerViewAdapter
import ru.don.eshope.ui.add_purchase_screen.AddPurchasesActivity
import ru.don.eshope.utils.dip


class PurchasesActivity : BaseActivity<ActivityPurchasesBinding>() {

    override val layoutId = R.layout.activity_purchases
    private val listVM: PurchasesListViewModel by viewModel()
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
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = RecyclerViewAdapter<Purchase, PurchasesListViewModel>(
            R.layout.item_purchase,
            listVM
        )

        adapter.items = vm.purchases
        rv.adapter = adapter

        val sectionItemDecoration =
            HeaderItemDecoration(dip(46), true, object : HeaderItemDecoration.SectionCallback {
                override fun isSection(position: Int): Boolean {
                    return position == 0 ||
                            vm.purchases.value?.get(position)?.date !=
                            vm.purchases.value?.get(position - 1)?.date
                }

                override fun getSectionHeader(position: Int): CharSequence {
                    return vm.purchases.value?.get(position)?.date ?: ":("
                }

            })
        rv.addItemDecoration(sectionItemDecoration)

        vm.purchases.observe(
            {
                lifecycle
            }, {
                adapter.notifyDataSetChanged()
            }
        )
    }

}