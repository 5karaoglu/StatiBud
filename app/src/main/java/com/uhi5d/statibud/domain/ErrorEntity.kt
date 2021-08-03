package com.uhi5d.statibud.domain

sealed class ErrorEntity{

    object NoContent : ErrorEntity()

    object NotModified : ErrorEntity()

    object  BadRequest : ErrorEntity()

    object Unauthorized : ErrorEntity()

    object Forbidden: ErrorEntity()

    object NotFound: ErrorEntity()

    object TooManyRequests: ErrorEntity()

    object InternalServerError: ErrorEntity()

    object BadGateway: ErrorEntity()

    object ServiceUnavailable: ErrorEntity()

    object Unknown: ErrorEntity()
}
