package ru.don.eshope.ui.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter<T, VM : ViewModel>(
    @LayoutRes private val layoutId: Int,
    private val vm: VM
) : RecyclerView.Adapter<BindingViewHolder>() {

    private val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingViewHolder
        .create(layoutId, parent)

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) = holder
        .bind(vm, items[position])

    override fun getItemCount(): Int = items.size

    fun clearAndAdd(list: List<T>){
        clear()
        addAll(list)
    }

    fun addAll(list: List<T>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun getItems() = items
}