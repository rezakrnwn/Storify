package com.rezakur.storify.presentation.login.viewmodels

import com.rezakur.storify.core.utils.Either
import com.rezakur.storify.core.utils.Failure
import com.rezakur.storify.core.utils.Validators
import com.rezakur.storify.core.utils.dispatcher.DispatcherProvider
import com.rezakur.storify.domain.usecases.LoginUseCase
import com.rezakur.storify.domain.usecases.LoginUseCaseParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var validators: Validators
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var loginViewModel: LoginViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        validators = mock()
        loginUseCase = mock()
        dispatcherProvider = object : DispatcherProvider {
            override val main: CoroutineDispatcher = testDispatcher
            override val io: CoroutineDispatcher = testDispatcher
            override val default: CoroutineDispatcher = testDispatcher
            override val unconfined: CoroutineDispatcher = testDispatcher

        }
        loginViewModel = LoginViewModel(
            validators = validators,
            loginUseCase = loginUseCase,
            dispatcherProvider = dispatcherProvider
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onEmailChanged should updates email and validation error when format is invalid`() =
        runTest {
            // arrange
            val email = "reza !getnada.com"
            val errorMessage = "Invalid email format"
            whenever(validators.validateEmail(email)).thenReturn(errorMessage)

            // act
            loginViewModel.onEmailChanged(email)

            // assert
            Assert.assertTrue(loginViewModel.loginState.value.email.isNotEmpty())
            Assert.assertTrue(loginViewModel.loginState.value.emailErrorMessage?.isNotEmpty() == true)
        }

    @Test
    fun `onPasswordChanged should update password and error message when it does not meet minimum requirement`() =
        runTest {
            // arrange
            val password = "123"
            val errorMessage = "Password min. 8 char"

            // act
            loginViewModel.onPasswordChanged(password)

            // assert
            Assert.assertTrue(loginViewModel.loginState.value.password.isNotEmpty())
            Assert.assertEquals(loginViewModel.loginState.value.password, password)
            Assert.assertEquals(loginViewModel.loginState.value.passwordErrorMessage, errorMessage)
        }

    @Test
    fun `login should update LoginStatus-SUCCESS when login is successful`() = testScope.runTest {
        // arrange
        val email = "reza@getnada.com"
        val password = "12345678"
        loginViewModel.onEmailChanged(email)
        loginViewModel.onPasswordChanged(password)
        whenever(loginUseCase.invoke(LoginUseCaseParams(email, password))).thenReturn(
            Either.Right(
                Pair(true, "login success")
            )
        )

        // act
        loginViewModel.login()
        advanceUntilIdle()

        // assert
        val loginState = loginViewModel.loginState.value
        Assert.assertEquals(loginState.email, email)
        Assert.assertEquals(loginState.password, password)
        Assert.assertTrue(loginState.loginStatus == LoginStatus.SUCCESS)
    }

    @Test
    fun `login should update LoginStatus-FAILED when login is failed`() = testScope.runTest {
        // arrange
        val email = "reza@getnada.com"
        val password = "11111111111"
        loginViewModel.onEmailChanged(email)
        loginViewModel.onPasswordChanged(password)
        whenever(loginUseCase.invoke(LoginUseCaseParams(email, password))).thenReturn(
            Either.Left(
                Failure.NetworkError(401, "login failed")
            )
        )

        // act
        loginViewModel.login()
        advanceUntilIdle()

        // assert
        val loginState = loginViewModel.loginState.value
        Assert.assertEquals(loginState.email, email)
        Assert.assertEquals(loginState.password, password)
        Assert.assertTrue(loginState.loginStatus == LoginStatus.FAILED)
    }
}