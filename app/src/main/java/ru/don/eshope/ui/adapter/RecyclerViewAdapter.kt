package ru.don.eshope.ui.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter<T, VM : ViewModel>(
    @LayoutRes private val layoutId: Int,
    private val vm: VM
) : RecyclerView.Adapter<BindingViewHolder>() {

    var items = MutableLiveData<List<T>>(listOf())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingViewHolder
        .create(layoutId, parent)

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) = holder
        .bind(vm, items.value?.get(position))

    override fun getItemCount(): Int = items.value?.size ?: 0

}