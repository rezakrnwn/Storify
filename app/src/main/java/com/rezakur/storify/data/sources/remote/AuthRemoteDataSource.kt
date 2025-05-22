package com.rezakur.storify.data.sources.remote

import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import com.rezakur.storify.data.sources.remote.response.LoginResponse

interface AuthRemoteDataSource {
    suspend fun login(
        email: String,
        password: String
    ): Either<Failure,LoginResponse>
}