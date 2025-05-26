package com.rezakur.storify.data.sources.remote

import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import com.rezakur.storify.data.sources.remote.response.LoginResponse
import retrofit2.HttpException
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(private val storifyApi: StorifyApi) : AuthRemoteDataSource {
    override suspend fun login(
        email: String,
        password: String
    ): Either<Failure, LoginResponse> {
        try {
            val result = storifyApi.login(email = email, password = password)
            return Either.Right(result)
        } catch (e: HttpException) {
            return Either.Left(Failure.NetworkError(errorCode = e.code(), message = e.message))
        }
    }
}