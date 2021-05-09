package com.example.selfwich.ui.login

import com.google.firebase.auth.FirebaseUser

data class LoginResult(
    val success: FirebaseUser? = null,
    val error: String? = null
)