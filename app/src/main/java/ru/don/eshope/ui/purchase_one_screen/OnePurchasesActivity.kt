package ru.don.eshope.ui.purchase_one_screen

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.roonyx.orcheya.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_purchases.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Item
import ru.don.eshope.database.entities.PurchaseAndItems
import ru.don.eshope.databinding.ActivityOnePurchaseBinding
import ru.don.eshope.ui.adapter.RecyclerViewAdapter
import ru.don.eshope.ui.edit_purchase.EditPurchasesActivity
import ru.don.eshope.ui.edit_purchase.EditPurchasesActivity.Companion.ID_EDIT

class OnePurchasesActivity : BaseActivity<ActivityOnePurchaseBinding>(), IOnePurchasesViewModel {

    companion object {
        val ID = "ID_PURCHASE"
    }

    private var purchaseId: Int = -1
    override val layoutId = R.layout.activity_one_purchase
    private val vm: OnePurchasesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = vm
        vm.listener = this

        purchaseId = intent.getIntExtra(ID, 0)

        initItemsRv()
    }

    private fun initItemsRv() {
        vm.getItemsByPurchaseId(purchaseId)
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

    override fun edit(purchase: PurchaseAndItems) {
        val intent = Intent(this, EditPurchasesActivity::class.java).apply {
            putExtra(ID_EDIT, purchase.id)
        }
        startActivity(intent)
    }

    override fun onResume() {
        vm.getItemsByPurchaseId(purchaseId)
        super.onResume()
    }

}