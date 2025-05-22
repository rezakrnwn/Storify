package com.rezakur.storify.data.repositories

import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import com.rezakur.storify.data.sources.remote.AuthRemoteDataSource
import com.rezakur.storify.domain.repositories.AuthRepository

class AuthRepositoryImpl(private val authRemoteDataSource: AuthRemoteDataSource) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Either<Failure, Pair<Boolean, String>> {
        val result = authRemoteDataSource.login(email = email, password = password)
        return when (result) {
            is Either.Right -> Either.Right(
                Pair(
                    result.value.error == false,
                    result.value.message ?: ""
                )
            )

            is Either.Left -> Either.Left(result.value)
        }
    }
}