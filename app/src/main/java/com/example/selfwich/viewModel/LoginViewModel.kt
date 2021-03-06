package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.R
import com.example.selfwich.repository.AuthRepository
import com.example.selfwich.repository.AuthStatus
import com.example.selfwich.ui.login.LoginForm
import com.example.selfwich.ui.login.LoginFormState
import com.example.selfwich.ui.login.LoginResult

class LoginViewModel(app: Application, val authRepository: AuthRepository) : ViewModel() {

    private val _form = MutableLiveData<LoginForm>()
    val form: LiveData<LoginForm> = _form

    private val _navigateRegisterScreen = MutableLiveData<Boolean>()
    val navigateRegisterScreen: LiveData<Boolean> = _navigateRegisterScreen

    val loginStatus: LiveData<AuthStatus> = authRepository.authStatus
    val fireBaseAuthResult: LiveData<LoginResult> = authRepository.fireBaseAuthResult

    private val _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    fun loginUser(email:String, password:String){
        authRepository.loginUser(email, password)

    }
    fun navigateRegisterScreen() {
        _navigateRegisterScreen.value = true
    }

    fun navigateEventRegisterScreenDone() {
        _navigateRegisterScreen.value = false
    }

    fun loginTextValidation(username: String, password: String) {

        if (!isUserNameValid(username)) {

            _loginFormState.value = LoginFormState(usernameError = R.string.user_name_is_not_valid)

        } else if (!isPasswordValid(password)) {

            _loginFormState.value = LoginFormState(passwordError = R.string.password_is_not_valid)

        } else {

            _loginFormState.value = LoginFormState(isDataValid = true)
            _form.value = LoginForm(username, password)
        }

    }
    private fun isUserNameValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
    open class Factory(val app: Application, private val authRepository: AuthRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(app, authRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}