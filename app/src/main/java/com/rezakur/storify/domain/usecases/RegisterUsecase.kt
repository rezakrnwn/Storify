package com.rezakur.storify.domain.usecases

import com.rezakur.storify.core.base.UseCase
import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import com.rezakur.storify.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) :
    UseCase<RegisterUseCaseParams, Either<Failure, String>> {

    override suspend fun invoke(params: RegisterUseCaseParams): Either<Failure, String> =
        authRepository.register(
            name = params.name,
            email = params.email,
            password = params.password
        )
}

data class RegisterUseCaseParams(
    val name: String,
    val email: String,
    val password: String
)