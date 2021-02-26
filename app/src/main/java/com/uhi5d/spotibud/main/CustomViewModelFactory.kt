package com.uhi5d.spotibud.main

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomViewModelFactory(private val lifecycleOwner: LifecycleOwner, val context: Context):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LifecycleOwner::class.java,Context::class.java).newInstance(lifecycleOwner,context)
    }
}