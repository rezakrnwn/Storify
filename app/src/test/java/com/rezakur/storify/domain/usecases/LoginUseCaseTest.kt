package com.rezakur.storify.domain.usecases

import com.rezakur.storify.DummyData
import com.rezakur.storify.core.extensions.getLeft
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

class LoginUseCaseTest {
    private lateinit var authRepository: AuthRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        authRepository = mock()
        loginUseCase = LoginUseCase(authRepository)
    }

    @Test
    fun `login should return Either Right when repository return success`() = runTest {
        // arrange
        val email = "reza@getnada.com"
        val password = "11233455"
        val expectedResult = Either.Right(Pair(true, "-"))
        whenever(authRepository.login(email, password)).thenReturn(expectedResult)

        // act
        val result = loginUseCase.invoke(LoginUseCaseParams(email, password))

        // assert
        verify(authRepository, times(1)).login(email, password)
        Assert.assertTrue(result.isRight)
    }

    @Test
    fun `login should return Either Left when repository return 401 Unauthorized`() = runTest {
        // arrange
        val email = "wrong@email.com"
        val password = "222wrong"
        val expectedResult = Either.Left(
            Failure.NetworkError(
                errorCode = DummyData.loginResponseUnauthorized().code(),
                message = "error"
            )
        )
        whenever(authRepository.login(email, password)).thenReturn(expectedResult)

        // act
        val result = loginUseCase.invoke(LoginUseCaseParams(email, password))

        // assert
        verify(authRepository, times(1)).login(email, password)
        Assert.assertTrue(result.isLeft)
        Assert.assertEquals(
            (result.getLeft() as Failure.NetworkError).errorCode,
            expectedResult.getLeft()?.errorCode
        )
    }
}