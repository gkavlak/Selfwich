package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.repository.AuthRepository
import com.example.selfwich.ui.login.LoginResult
import com.example.selfwich.ui.register.RegisterForm
import com.example.selfwich.R
import com.example.selfwich.repository.AuthStatus
import com.example.selfwich.ui.register.RegisterFormState

class RegisterViewModel(val app:Application, private val authRepository: AuthRepository):
    ViewModel() {

    val firebaseAuthResult: LiveData<LoginResult> = authRepository.fireBaseAuthResult

    private val _registerFormState = MutableLiveData<RegisterFormState>()

    val registerStatus: LiveData<AuthStatus> = authRepository.authStatus

    val registerFormState: LiveData<RegisterFormState> = _registerFormState

    private val _registerForm = MutableLiveData<RegisterForm>()
    val registerForm: LiveData<RegisterForm> = _registerForm

    fun registerUser(email: String, password: String, name: String) {
        authRepository.registerUser(email, password, name)
    }
    fun registerTextValidation(username: String, password: String, name: String) {

        if (!isUserNameValid(username)) {

            _registerFormState.value =
                RegisterFormState(useremailError = R.string.user_name_is_not_valid)

        } else if (!isPasswordValid(password)) {

            _registerFormState.value =
                RegisterFormState(passwordError = R.string.password_is_not_valid)

        } else if (!isNameValid(name)) {
            _registerFormState.value = RegisterFormState(usernameError = R.string.name_is_not_valid)
        } else {

            _registerFormState.value = RegisterFormState(isDataValid = true)
            _registerForm.value = RegisterForm(username, password, name)
        }

    }
    private fun isUserNameValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isNameValid(name: String): Boolean {
        return name.length > 2
    }






    open class Factory(val app: Application, private val authRepository: AuthRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(app, authRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }

}