package ru.don.eshope.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.don.eshope.R
import ru.don.eshope.database.entities.Purchase
import ru.don.eshope.models.DateAmount
import ru.don.eshope.ui.purchases_screen.PurchasesViewModel

class RecyclerViewAdapterPurchases(
    val vm: PurchasesViewModel
) : RecyclerView.Adapter<BindingViewHolder>() {

    var items: List<Any> = listOf()

    val header = 0
    val purchase = 1

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DateAmount -> header
            is Purchase -> purchase
            else -> throw (Exception("Cannot get ViewType for ${items[position]}"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            header -> BindingViewHolder.create(R.layout.recycler_section_header, parent)
            purchase -> BindingViewHolder.create(R.layout.item_purchase, parent)
            else -> throw (Exception("Unsupported viewType"))
        }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) = holder
        .bind(vm, items[position])

    override fun getItemCount(): Int = items.size ?: 0

}