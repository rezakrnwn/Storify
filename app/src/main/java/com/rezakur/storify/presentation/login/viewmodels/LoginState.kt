package com.rezakur.storify.presentation.login.viewmodels

data class LoginState(
    val loginStatus: LoginStatus = LoginStatus.INITIAL,
    val email: String = "",
    val password: String = "",
    val isFormValid: Boolean = false,
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
)

enum class LoginStatus {
    INITIAL,
    LOADING,
    SUCCESS,
    FAILED,
}