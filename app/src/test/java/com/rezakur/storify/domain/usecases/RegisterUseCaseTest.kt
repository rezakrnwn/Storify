package com.rezakur.storify.domain.usecases

import com.rezakur.storify.DummyData
import com.rezakur.storify.core.extensions.getLeft
import com.rezakur.storify.core.extensions.getRight
import com.rezakur.storify.core.extensions.isLeft
import com.rezakur.storify.core.extensions.isRight
import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import com.rezakur.storify.domain.repositories.AuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class RegisterUseCaseTest {
    lateinit var authRepository: AuthRepository
    lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setUp() {
        authRepository = mock()
        registerUseCase = RegisterUseCase(authRepository)
    }

    @Test
    fun `register should return Either Right when auth repository return Either Right`() = runTest {
        // arrange
        val name = "Reza K."
        val email = "reza@getnada.com"
        val password = "1233456"
        val expectedResult = Either.Right(
            DummyData.registerResponseSuccess().message ?: ""
        )
        whenever(authRepository.register(name, email, password)).thenReturn(expectedResult)

        // act
        val result = registerUseCase.invoke(RegisterUseCaseParams(name, email, password))

        // assert
        verify(authRepository, times(1)).register(name, email, password)
        Assert.assertTrue(result.isRight)
        Assert.assertEquals(expectedResult.value, result.getRight())
    }

    @Test
    fun `register should return Either Left when auth repository return Either Left`() = runTest {
        // arrange
        val name = "Reza K."
        val email = "reza@getnada.com"
        val password = "1233456"
        val expectedResult = Either.Left(
            Failure.NetworkError(
                DummyData.registerResponseFailed().code(),
                message = DummyData.registerResponseFailed().message
            )
        )
        whenever(authRepository.register(name, email, password)).thenReturn(expectedResult)

        // act
        val result = registerUseCase.invoke(RegisterUseCaseParams(name, email, password))

        // assert
        verify(authRepository, times(1)).register(name, email, password)
        Assert.assertTrue(result.isLeft)
        Assert.assertEquals(
            expectedResult.getLeft()?.errorCode,
            (result.getLeft() as Failure.NetworkError).errorCode
        )
    }
}