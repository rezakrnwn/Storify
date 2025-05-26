package com.rezakur.storify.presentation.login.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezakur.storify.core.extensions.isRight
import com.rezakur.storify.core.utils.Validators
import com.rezakur.storify.core.utils.dispatcher.DispatcherProvider
import com.rezakur.storify.domain.usecases.LoginUseCase
import com.rezakur.storify.domain.usecases.LoginUseCaseParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validators: Validators,
    private val loginUseCase: LoginUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun onEmailChanged(email: String) {
        _loginState.update {
            it.copy(email = email, emailErrorMessage = validators.validateEmail(email))
        }
    }

    fun onPasswordChanged(password: String) {
        _loginState.update {
            it.copy(
                password = password,
                passwordErrorMessage = if (password.length < 8) "Password min. 8 char" else null
            )
        }
    }

    fun login() {
        viewModelScope.launch {
            _loginState.update {
                it.copy(loginStatus = LoginStatus.LOADING)
            }

            val result = withContext(dispatcherProvider.io) {
                loginUseCase.invoke(
                    LoginUseCaseParams(
                        _loginState.value.email,
                        _loginState.value.password
                    )
                )
            }

            _loginState.update {
                it.copy(loginStatus = if (result.isRight) LoginStatus.SUCCESS else LoginStatus.FAILED)
            }
        }
    }
}