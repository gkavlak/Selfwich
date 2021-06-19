package com.example.selfwich.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.selfwich.R
import com.example.selfwich.databinding.LoginFragmentBinding
import com.example.selfwich.repository.AuthRepository
import com.example.selfwich.viewModel.LoginViewModel

class LoginFragment: Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.login_fragment,container,false)
        emailText=binding.emailText
        passwordText=binding.passwordText

        emailText.doAfterTextChanged {
            viewModel.loginTextValidation(
                it.toString(),
                passwordText.text.toString()
            )
            passwordText.doAfterTextChanged {
                viewModel.loginTextValidation(
                    emailText.text.toString(),
                            it.toString()
                )
            }
        }

        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        authRepository = AuthRepository()
        val activity = requireNotNull(this.activity) {}

        viewModel= ViewModelProvider(this,LoginViewModel.Factory(activity.application,authRepository))
            .get(LoginViewModel::class.java)

        viewModel.fireBaseAuthResult.observe(viewLifecycleOwner, Observer {
            if(it.success != null){
                updateUiForLoggedInUser()
            }else if (it.error != null)
                showLoginFailed(it.error)
        })

            viewModel.loginFormState.observe(viewLifecycleOwner, Observer { loginFormState ->
                binding.loginButton.isEnabled= loginFormState.isDataValid
                if(loginFormState.usernameError != null){
                    emailText.error=getString(loginFormState.usernameError)
                }

                else if (loginFormState.passwordError != null){
                    passwordText.error=getString(loginFormState.passwordError)
                }

            })

            binding.loginToRegisterButton2.setOnClickListener {
                goLoginToRegister()
            }




        binding.viewmodel= viewModel
        binding.lifecycleOwner = this
    }


    private fun updateUiForLoggedInUser() {
        this.findNavController()
            .navigate(LoginFragmentDirections.actionLoginFragmentToDrinksFragment())
    }

    private fun goLoginToRegister(){
        this.findNavController()
            .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun showLoginFailed(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }







}