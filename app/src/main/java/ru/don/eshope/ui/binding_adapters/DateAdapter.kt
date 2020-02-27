package ru.don.eshope.ui.binding_adapters

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import ru.don.eshope.utils.getTimeByPattern

@BindingAdapter("date")
fun AppCompatTextView.setMyDate(date: Long) {
    val str = date.getTimeByPattern()
    text = str
}