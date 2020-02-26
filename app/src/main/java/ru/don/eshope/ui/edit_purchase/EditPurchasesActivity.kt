package ru.don.eshope.ui.edit_purchase

import android.app.Dialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.roonyx.orcheya.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_purchases.*
import kotlinx.android.synthetic.main.activity_add_purchases.view.*
import kotlinx.android.synthetic.main.activity_purchases.rv
import kotlinx.android.synthetic.main.dialog_add_purchase.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.don.eshope.R
import ru.don.eshope.database.entities.Item
import ru.don.eshope.databinding.ActivityEditPurchaseBinding
import ru.don.eshope.ui.adapter.RecyclerViewAdapter
import ru.don.eshope.ui.add_purchase_screen.AddPurchasesListViewModel
import ru.don.eshope.ui.base.IPurchase

class EditPurchasesActivity : BaseActivity<ActivityEditPurchaseBinding>(), IPurchase {

    companion object {
        val ID_EDIT = "ID_EDIT_PURCHASE"
        val TAG = EditPurchasesActivity::class.java.simpleName
    }

    override val layoutId = R.layout.activity_edit_purchase
    private val vm: EditPurchasesViewModel by viewModel()
    private val listVM: AddPurchasesListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = vm

        vm.init(intent.getIntExtra(ID_EDIT, -1))

        vm.listener = this
        initPurchasesRv()

        initDeleteItemAction()
        initEditAction()
    }

    private fun editItem(item: Item) {
        val view = layoutInflater.inflate(R.layout.dialog_add_purchase, root, false)
        view.apply {
            et_name.setText(item.name)
            et_price.setText(item.price.toString())
        }

        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.edit_item))
            .setTitle(getString(R.string.edit))
            .setView(view)
            .setPositiveButton(getString(R.string.save)) { d, _ ->
                val name =
                    (d as Dialog).findViewById<TextInputEditText>(R.id.et_name)
                        ?.text?.toString()
                val price =
                    d.findViewById<TextInputEditText>(R.id.et_price)?.text?.toString()

                if (vm.validateNamePrice(name, price, item)) {
                    val lm = rv.layoutManager as LinearLayoutManager
                    root.rv.adapter?.notifyDataSetChanged()
                }
            }
            .show()
    }

    private fun initEditAction() {
        listVM.edititem.observe({ lifecycle }, {
            editItem(it)
        })
    }

    private fun initDeleteItemAction() {
        listVM.deleteItem.observe({ lifecycle }, {
            vm.deleteItem(it)
            rv.adapter?.notifyDataSetChanged()
        })
    }

    private fun initPurchasesRv() {
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = RecyclerViewAdapter<Item, AddPurchasesListViewModel>(
            R.layout.item_purchase_item,
            listVM
        )

        adapter.items = vm.items
        rv.adapter = adapter

        initMsgListener()

        vm.items.observe(
            { lifecycle }, {
                adapter.notifyDataSetChanged()
            }
        )
    }

    private fun initMsgListener() {
        vm.validateErrorMsg.observe({ lifecycle }, {
            createSnackBar(it)
        })
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

                vm.validateNamePrice(name, price)
            }
            .show()
    }

    override fun changeTime(time: Long) {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .setSelection(time)
            .build().apply {
                addOnPositiveButtonClickListener {
                    vm.selectDate(it)
                }
            }.show(supportFragmentManager, TAG)
    }

    override fun back() {
        finish()
    }

    override fun emptyBasket() = createSnackBar(getString(R.string.empty_basket))

    override fun emptyName() = createSnackBar(getString(R.string.enter_name_pls))

    private fun createSnackBar(msg: String) =
        Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()

}