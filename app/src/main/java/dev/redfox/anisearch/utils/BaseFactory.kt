package dev.redfox.anisearch.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
    }

fun ViewModelStoreOwner.getModelView(viewModel: ViewModel): ViewModel {
    val provider = ViewModelProvider(this, viewModelFactory { viewModel })
    return provider[viewModel::class.java]
}