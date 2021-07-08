package com.uhi5d.spotibud.util

sealed class DataState<out B> {
    data class Success<out A>(val data: A) : DataState<A>()
    data class Error(val e: Throwable) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
