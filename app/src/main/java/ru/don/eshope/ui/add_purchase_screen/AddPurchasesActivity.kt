package ru.don.eshope.ui.add_purchase_screen

import android.app.Dialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.roonyx.orcheya.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_purchases.*
import kotlinx.android.synthetic.main.activity_purchases.rv
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Item
import ru.don.eshope.databinding.ActivityAddPurchasesBinding
import ru.don.eshope.ui.adapter.RecyclerViewAdapter

class AddPurchasesActivity : BaseActivity<ActivityAddPurchasesBinding>(), IAddPurchase,
    AddPurchasesListListener {

    override val layoutId = R.layout.activity_add_purchases
    private val vm: AddPurchasesViewModel by viewModel()
    private val listVM: AddPurchasesListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = vm

        vm.listener = this
        initPurchasesRv()

    }

    private fun initPurchasesRv() {
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listVM.listener = this

        val adapter = RecyclerViewAdapter<Item, AddPurchasesListViewModel>(
            R.layout.item_purchase_item,
            listVM
        )

        adapter.items = vm.items
        rv.adapter = adapter

        vm.items.observe(
            { lifecycle }, {
                adapter.notifyDataSetChanged()
            }
        )
    }

    override fun addItem() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.add_item))
            .setTitle(getString(R.string.new_item))
            .setView(R.layout.dialog_add_purchase)
            .setPositiveButton(getString(R.string.save)) { d, _ ->
                val name =
                    (d as Dialog).findViewById<TextInputEditText>(R.id.et_name)?.text?.toString()
                val price =
                    d.findViewById<TextInputEditText>(R.id.et_price)?.text?.toString()
                        ?.toDouble()
                vm.addNewItem(name, price)
            }
            .show()
    }

    override fun back() {
        finish()
    }

    override fun emptyBasket() =
        Snackbar.make(root, getString(R.string.empty_basket), Snackbar.LENGTH_SHORT).show()

    override fun emptyName() =
        Snackbar.make(root, getString(R.string.enter_name_pls), Snackbar.LENGTH_SHORT).show()

    override fun onDelete(item: Item) {
        vm.deleteItem(item)
        rv.adapter?.notifyDataSetChanged()
    }

}