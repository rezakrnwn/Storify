package com.rezakur.storify.core.utils

sealed class Failure {
    data class NetworkError(val errorCode: Int? = null, val message: String? = null) : Failure()
    data class DatabaseError(val message: String? = null, val throwable: Throwable? = null) : Failure()
    data class UnknownError(val throwable: Throwable? = null) : Failure()
}