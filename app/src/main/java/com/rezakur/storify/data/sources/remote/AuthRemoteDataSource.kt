package com.rezakur.storify.data.sources.remote

import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import com.rezakur.storify.data.sources.remote.response.LoginResponse
import com.rezakur.storify.data.sources.remote.response.RegisterResponse

interface AuthRemoteDataSource {
    suspend fun login(
        email: String,
        password: String
    ): Either<Failure, LoginResponse>

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ): Either<Failure, RegisterResponse>
}