package com.uhi5d.spotibud.data.remote

import okhttp3.ResponseBody

sealed class ApiResponse<out R> {
    object Loading: ApiResponse<Nothing>()
    data class Successful<out S>(val data: S) : ApiResponse<S>()
    object Created : ApiResponse<Nothing>()
    object Accepted : ApiResponse<Nothing>()
    object NoContent : ApiResponse<Nothing>()
    object NotModified : ApiResponse<Nothing>()
    data class BadRequest(val errorBody: ResponseBody) : ApiResponse<Nothing>()
    data class Unauthorized(val errorBody: ResponseBody) : ApiResponse<Nothing>()
    object Forbidden : ApiResponse<Nothing>()
    object NotFound : ApiResponse<Nothing>()
    data class TooManyRequests(val time: String) : ApiResponse<Nothing>()
    object InternalServerError : ApiResponse<Nothing>()
    object BadGateway : ApiResponse<Nothing>()
    object ServiceUnavailable : ApiResponse<Nothing>()
}