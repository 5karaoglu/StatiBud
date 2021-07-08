package com.uhi5d.spotibud.main

import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomViewModelFactory constructor(
    @Keep val lifecycleOwner: LifecycleOwner,
    @Keep val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LifecycleOwner::class.java, Context::class.java)
            .newInstance(lifecycleOwner, context)
    }
}