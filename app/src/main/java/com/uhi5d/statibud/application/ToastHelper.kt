package com.uhi5d.statibud.application

import android.content.Context
import com.uhi5d.statibud.R
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ToastHelper
@Inject constructor(){
    private val toastEmitter: EventEmitter<String> = EventEmitter()
    val toastMessages: EventSource<String> = toastEmitter

    fun sendToast(message: String){
        toastEmitter.emit(message)
    }
    fun errorMessage(@ApplicationContext context: Context,message: String){
        toastEmitter.emit(String.format(context.getString(R.string.datastate_error),message))
    }
}