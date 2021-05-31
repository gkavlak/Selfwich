package com.example.dicode.ui.settings

data class SettingsFormState(
    val userEmailError: Int? = null,
    val passwordError: Int? = null,
    val userNameError: Int? = null,
    val isDataValid: Boolean = false
)