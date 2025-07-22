package com.rezakur.storify.domain.repositories

import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure

interface AuthRepository {
    suspend fun login(email: String, password: String): Either<Failure, Pair<Boolean, String>>

    suspend fun register(name: String, email: String, password: String): Either<Failure, String>
}