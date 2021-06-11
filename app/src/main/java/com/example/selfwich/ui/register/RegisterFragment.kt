package com.example.selfwich.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.selfwich.R
import com.example.selfwich.databinding.RegisterFragmentBinding
import com.example.selfwich.repository.AuthRepository
import com.example.selfwich.viewModel.RegisterViewModel
import java.util.Observer

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: RegisterFragmentBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var nameText: EditText



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(inflater)
        emailText = binding.registerEditTextEmail
        passwordText = binding.registerEditTextPassword
        nameText = binding.registerEditTextTextPersonName
        initRegisterFormChangeHandler()

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        authRepository = AuthRepository()
        val activity = requireNotNull(this.activity) {

        }
        viewModel = ViewModelProvider(
            this,
            RegisterViewModel.Factory(activity.application, authRepository)
        ).get(RegisterViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.firebaseAuthResult.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it.success != null){
                registerToIngredient()

               // updateUiForLoggedInUser()
            }else if (it.error != null)
                showLoginFailed(it.error)
        })
        viewModel.registerFormState.observe(viewLifecycleOwner, androidx.lifecycle.Observer { registerFormState ->

            binding.registerButton.isEnabled = registerFormState.isDataValid

            if (registerFormState.useremailError != null) {

                emailText.error = getString(registerFormState.useremailError)

            }
            if (registerFormState.passwordError != null) {

                passwordText.error = getString(registerFormState.passwordError)

            } else if (registerFormState.usernameError != null) {

                nameText.error = getString(registerFormState.usernameError)
            }
        })
    }


   /* private fun updateUiForLoggedInUser() {
        this.findNavController()
            .navigate(RegisterFragmentDirections.actionRegisterFragmentToDrinksFragment())
    }
*/
    private fun registerToIngredient() {
        this.findNavController()
            .navigate(RegisterFragmentDirections.actionRegisterFragmentToEatsFragment())
    }
    private fun showLoginFailed(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun initRegisterFormChangeHandler() {
        emailText.doAfterTextChanged {
            viewModel.registerTextValidation(
                it.toString(),
                passwordText.text.toString(),
                nameText.text.toString()
            )
        }

        passwordText.doAfterTextChanged {
            viewModel.registerTextValidation(
                emailText.text.toString(),
                it.toString(),
                nameText.text.toString()
            )
        }
        nameText.doAfterTextChanged {
            viewModel.registerTextValidation(
                emailText.text.toString(),
                passwordText.text.toString(),
                it.toString()
            )
        }
    }

}



