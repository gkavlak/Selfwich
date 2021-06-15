package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Product
import com.example.selfwich.repository.CustomDialogRepository
import com.example.selfwich.repository.UpdateDialogRepository

class UpdateDialogViewModel (app: Application ,private val updateDialogRepository: UpdateDialogRepository): ViewModel() {

    fun updateDrinks(oldproduct: Product, newproduct:Product){
        updateDialogRepository.updateDrinks(oldproduct = oldproduct, newproduct = newproduct)

    }

    open class Factory(val app: Application, private val updateDialogRepository: UpdateDialogRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UpdateDialogViewModel::class.java)) {
                return UpdateDialogViewModel(app, updateDialogRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}