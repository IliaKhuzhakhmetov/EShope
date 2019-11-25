package ru.don.eshope.ui.binding_adapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import ru.don.eshope.R

@BindingAdapter("isDay", requireAll = false)
fun AppCompatImageView.setTheme(isDay: Boolean?) {
    if (isDay != true) setImageResource(R.drawable.ic_moon) else setImageResource(R.drawable.ic_sun)
}