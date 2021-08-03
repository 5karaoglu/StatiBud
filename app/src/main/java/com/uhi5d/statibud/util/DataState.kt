package com.uhi5d.statibud.util

sealed class DataState<out B> {
    data class Success<out A>(val data: A) : DataState<A>()
    object Empty: DataState<Nothing>()
    data class Fail(val e: Throwable) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
