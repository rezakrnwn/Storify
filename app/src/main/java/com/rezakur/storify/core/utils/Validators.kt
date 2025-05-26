package com.rezakur.storify.core.utils

import android.util.Patterns
import javax.inject.Inject

interface Validators {
    fun validateEmail(email: String): String?
}

class DefaultValidators @Inject constructor() : Validators {
    override fun validateEmail(email: String): String? = when {
        email.isBlank() -> "Email cannot be empty"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
        else -> null
    }
}