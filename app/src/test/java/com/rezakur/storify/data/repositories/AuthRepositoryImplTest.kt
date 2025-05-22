package com.rezakur.storify.data.repositories

import com.rezakur.storify.DummyData
import com.rezakur.storify.core.extensions.getLeft
import com.rezakur.storify.core.extensions.isLeft
import com.rezakur.storify.core.extensions.isRight
import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import com.rezakur.storify.data.sources.remote.AuthRemoteDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AuthRepositoryImplTest {
    private lateinit var authRepositoryImpl: AuthRepositoryImpl
    private lateinit var authRemoteDataSource: AuthRemoteDataSource

    @Before
    fun setUp() {
        authRemoteDataSource = mock()
        authRepositoryImpl = AuthRepositoryImpl(authRemoteDataSource)
    }

    @Test
    fun `login should return Either Right when remote data source return success`() = runTest {
        // arrange
        val email = "reza@getnada.com"
        val password = "123444"
        val expectedResult = Either.Right(DummyData.loginResponseSuccess())
        whenever(authRemoteDataSource.login(email, password)).thenReturn(expectedResult)

        // act
        val result = authRepositoryImpl.login(email, password)

        // assert
        verify(authRemoteDataSource, times(1)).login(email, password)
        Assert.assertTrue(result.isRight)
        Assert.assertEquals((result as Either.Right).value.first, true)
    }

    @Test
    fun `login should return Either Left when remote data source return 401 Unauthorized`() = runTest {
        // arrange
        val email = "email@getnada.com"
        val password = "112233"
        val expectedResult = Either.Left(
            Failure.NetworkError(
                errorCode = DummyData.loginResponseUnauthorized().code(),
                message = DummyData.loginResponseUnauthorized().message()
            )
        )
        whenever(authRemoteDataSource.login(email, password)).thenReturn(expectedResult)

        // act
        val result = authRepositoryImpl.login(email, password)

        // assert
        verify(authRemoteDataSource, times(1)).login(email, password)
        Assert.assertTrue(result.isLeft)
        Assert.assertEquals(
            (result.getLeft() as Failure.NetworkError).errorCode,
            DummyData.loginResponseUnauthorized().code()
        )
    }
}