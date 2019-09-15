package ru.don.eshope.ui.purchases_screen

import android.os.Bundle
import com.roonyx.orcheya.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.don.eshope.R
import ru.don.eshope.databinding.ActivityPurchasesBinding

class PurchasesActivity : BaseActivity<ActivityPurchasesBinding>() {

    override val layoutId = R.layout.activity_purchases
    private val vm: PurchasesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = vm
    }
}