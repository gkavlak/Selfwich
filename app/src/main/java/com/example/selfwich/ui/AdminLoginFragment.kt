package com.example.selfwich.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.selfwich.databinding.AdminLoginFragmentBinding
import com.example.selfwich.repository.AdminLoginRepository
import com.example.selfwich.viewModel.AdminLoginViewModel

class AdminLoginFragment : Fragment() {

    private lateinit var binding: AdminLoginFragmentBinding
    private lateinit var viewModel: AdminLoginViewModel
    private lateinit var adminLoginRepository: AdminLoginRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminLoginFragmentBinding.inflate(inflater)

        return binding.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {}
         adminLoginRepository = AdminLoginRepository()
        viewModel = ViewModelProvider(
            this,
            AdminLoginViewModel.Factory(activity.application, adminLoginRepository)
            //  çift ünleme tekrar bak
        ).get(AdminLoginViewModel::class.java)

            adminLoginRepository.loginSucces.observe(viewLifecycleOwner, Observer {
                if(it == true){
                    updateUiForLoggedInUser()
                }
                else{
                    showLoginFailed(error= "FATAL 404 ERROR")
                }
            })

        binding.viewmodel= viewModel
        binding.lifecycleOwner= this

            binding.button6.setOnClickListener {
           val name  =  binding.emailTxt.text.toString()
            val pwd  =  binding.pwdTxt.text.toString()

         viewModel.adminLogin(email = name ,password = pwd)
        }

    }
    private fun updateUiForLoggedInUser() {
        this.findNavController()
            .navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToEatsFragment())
    }

    private fun showLoginFailed(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }


}