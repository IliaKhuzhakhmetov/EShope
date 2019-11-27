package ru.don.eshope.ui.purchase_one_screen

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.roonyx.orcheya.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_purchases.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Item
import ru.don.eshope.databinding.ActivityOnePurchaseBinding
import ru.don.eshope.ui.adapter.RecyclerViewAdapter

class OnePurchasesActivity : BaseActivity<ActivityOnePurchaseBinding>(), IOnePurchasesViewModel {

    companion object {
        val ID = "ID_PURCHASE"
    }

    override val layoutId = R.layout.activity_one_purchase
    private val vm: OnePurchasesViewModel by viewModel()
    private val listVM: OnePurchasesListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = vm
        vm.listener = this

        initItemsRv()
    }

    private fun initItemsRv() {
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = RecyclerViewAdapter<Item, OnePurchasesListViewModel>(
            R.layout.item_purchase_item_without_delete,
            listVM
        )

        adapter.items = vm.items
        rv.adapter = adapter

        vm.items.observe(
            { lifecycle }, {
                adapter.notifyDataSetChanged()
            }
        )

        val id = intent.getIntExtra(ID, 0)
        vm.getItemsByPurchaseId(id)
    }

    override fun back() {
        finish()
    }

    override fun deleteDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.delete_purchase))
            .setMessage(getString(R.string.delete_purchase_info))
            .setPositiveButton(getString(R.string.yes)) { d, _ ->
                vm.deletePurchase()
                d.dismiss()
            }
            .setNegativeButton(getString(R.string.no)) { d, _ ->
                d.dismiss()
            }
            .show()
    }

}