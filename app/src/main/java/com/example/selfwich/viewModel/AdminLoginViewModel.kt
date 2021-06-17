package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.DomainUser
import com.example.selfwich.repository.AdminLoginRepository
import com.example.selfwich.repository.EatsRepository

class AdminLoginViewModel (app : Application ,  private val adminLoginRepository: AdminLoginRepository): ViewModel()
{
        val loginSuccess : LiveData<Boolean> = adminLoginRepository.loginSucces


        fun adminLogin(email :String ,password: String){
            adminLoginRepository.adminLogin(email, password)
        }

    open class Factory(val app: Application, private val adminLoginRepository: AdminLoginRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AdminLoginViewModel::class.java)) {
                return AdminLoginViewModel(app, adminLoginRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}