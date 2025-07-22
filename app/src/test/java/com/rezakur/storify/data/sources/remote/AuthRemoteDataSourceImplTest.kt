package com.rezakur.storify.data.sources.remote

import com.rezakur.storify.DummyData
import com.rezakur.storify.MainDispatcherRule
import com.rezakur.storify.core.extensions.getLeft
import com.rezakur.storify.core.extensions.getRight
import com.rezakur.storify.core.extensions.isLeft
import com.rezakur.storify.core.extensions.isRight
import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.atLeast
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AuthRemoteDataSourceImplTest {
    private lateinit var authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    private lateinit var storifyApi: StorifyApi

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        storifyApi = mock()
        authRemoteDataSourceImpl = AuthRemoteDataSourceImpl(storifyApi)
    }

    // LOGIN
    @Test
    fun `login should return Either Right when api return 200 OK`() = runTest {
        // arrange
        val email = "reza@getnada.com"
        val password = "123456"
        val response = DummyData.loginResponseSuccess()
        whenever(storifyApi.login(email = email, password = password)).thenReturn(
            response
        )

        // act
        val result = authRemoteDataSourceImpl.login(email = email, password = password)

        // assert
        verify(storifyApi).login(email = email, password = password)
        verify(storifyApi, times(1)).login(email = email, password = password)
        verify(storifyApi).login(email = eq(email), password = eq(password))
        Assert.assertTrue(result.isRight)
        Assert.assertEquals(response.error, (result as Either.Right).value.error)
        Assert.assertEquals(response.loginResult?.token, result.value.loginResult?.token)
    }

    @Test
    fun `login should return Either Left when api return 401 Unauthorized`() = runTest {
        // arrange
        val email = "wrong@email.com"
        val password = "wrong password"
        val exception = DummyData.loginResponseUnauthorized()
        val emailCaptor = argumentCaptor<String>()
        val passwordCaptor = argumentCaptor<String>()
        whenever(storifyApi.login(email = email, password = password)).thenThrow(exception)

        // act
        val result = authRemoteDataSourceImpl.login(email = email, password = password)

        // assert
        verify(storifyApi).login(emailCaptor.capture(), passwordCaptor.capture())
        Assert.assertEquals(email, emailCaptor.firstValue)
        Assert.assertEquals(password, passwordCaptor.firstValue)
        verify(storifyApi, atLeast(1)).login(any(), any())
        Assert.assertTrue(result.isLeft)
        Assert.assertTrue(result.getLeft() is Failure.NetworkError)
        Assert.assertEquals(exception.code(), (result.getLeft() as Failure.NetworkError).errorCode)
    }

    // REGISTER
    @Test
    fun `register should return Either Right when api return 200 OK`() = runTest {
        // arrange
        val name = "Reza K."
        val email = "rez@getnada.com"
        val password = "123321"
        val response = DummyData.registerResponseSuccess()
        whenever(storifyApi.register(name = name, email = email, password = password)).thenReturn(
            response
        )

        // act
        val result =
            authRemoteDataSourceImpl.register(name = name, email = email, password = password)

        // assert
        verify(storifyApi).register(name = name, email = email, password = password)
        verify(storifyApi, atLeast(1)).register(any(), any(), any())
        Assert.assertTrue(result.isRight)
        Assert.assertEquals(response.error, result.getRight()?.error)
        Assert.assertEquals(response.message, result.getRight()?.message)
    }

    @Test
    fun `register should return Either Left when api return 400 Bad Request`() = runTest {
        // arrange
        val name = "Reza K."
        val email = "rez@getnada.com"
        val password = "123321"
        val exception = DummyData.registerResponseFailed()
        whenever(storifyApi.register(name = name, email = email, password = password)).thenThrow(
            exception
        )

        // act
        val result =
            authRemoteDataSourceImpl.register(name = name, email = email, password = password)

        // assert
        verify(storifyApi, atLeast(1)).register(any(), any(), any())
        Assert.assertTrue(result.isLeft)
        Assert.assertTrue(result.getLeft() is Failure.NetworkError)
        Assert.assertEquals(exception.code(), (result.getLeft() as Failure.NetworkError).errorCode)
    }
}