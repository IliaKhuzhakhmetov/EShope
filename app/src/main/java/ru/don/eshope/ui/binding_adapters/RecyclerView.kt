package ru.don.eshope.ui.binding_adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.don.eshope.ui.adapter.BindingViewHolder
import ru.don.eshope.ui.adapter.HeaderItemDecorationPurchase

@BindingAdapter("adapter")
fun RecyclerView.setMyAdapter(adapter: RecyclerView.Adapter<BindingViewHolder>?) {
    apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.adapter = adapter
    }
}

@BindingAdapter("decorator")
fun RecyclerView.setMyDecorator(decorator: HeaderItemDecorationPurchase?) {
    decorator?.let {
        addItemDecoration(decorator)
    }
}

