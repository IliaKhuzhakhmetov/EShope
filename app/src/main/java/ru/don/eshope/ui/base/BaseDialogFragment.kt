package ru.don.eshope.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<T : ViewDataBinding> : DialogFragment() {

    protected lateinit var binding: T

    abstract val layoutId: Int @LayoutRes get

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<T>(
        inflater, layoutId, container, false
    ).let {
        binding = it
        it.root
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

}