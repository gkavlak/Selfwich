package com.example.selfwich.ui.register

data class RegisterFormState(
    val useremailError: Int? = null,
    val passwordError: Int? = null,
    val usernameError: Int? = null,
    val isDataValid: Boolean = false
)