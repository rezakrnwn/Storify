package com.rezakur.storify.domain.usecases

import com.rezakur.storify.core.base.UseCase
import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import com.rezakur.storify.domain.repositories.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) :
    UseCase<LoginUseCaseParams, Either<Failure, Pair<Boolean, String>>> {

    override suspend fun invoke(params: LoginUseCaseParams): Either<Failure, Pair<Boolean, String>> =
        authRepository.login(email = params.email, password = params.password)
}

data class LoginUseCaseParams(
    val email: String,
    val password: String,
)