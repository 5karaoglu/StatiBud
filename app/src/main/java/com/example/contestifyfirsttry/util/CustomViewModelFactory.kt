package com.example.contestifyfirsttry.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomViewModelFactory(val lifecycleOwner: LifecycleOwner):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LifecycleOwner::class.java).newInstance(lifecycleOwner)
    }
}