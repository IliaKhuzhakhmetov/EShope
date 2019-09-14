package com.roonyx.orcheya.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.unSubscribeOnDestroy(): Disposable = this.apply { compositeDisposable.addAll(this) }

    override fun onCleared() {
        compositeDisposable.dispose()

        super.onCleared()
    }
}