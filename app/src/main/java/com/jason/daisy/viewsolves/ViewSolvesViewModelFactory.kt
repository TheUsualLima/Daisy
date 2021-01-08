package com.jason.daisy.viewsolves

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewSolvesViewModelFactory(private val application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewSolvesViewModel(application) as T
    }
}
