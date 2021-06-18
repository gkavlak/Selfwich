package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Ingredient
import com.example.selfwich.model.Product
import com.example.selfwich.model.Selfwich
import com.example.selfwich.repository.CustomDialogRepository

class CustomDialogViewModel (app:Application, private val customDialogRepository: CustomDialogRepository) : ViewModel() {

    private val _addEatsToDatabase: MutableLiveData<Product?> = MutableLiveData<Product?>(Product())
    val addEatsToDatabase: LiveData<Product?> = _addEatsToDatabase



    fun addIngredientToDatabase(ingredient: Ingredient){
        customDialogRepository.addIngredientToDatabase(ingredient)

    }

    fun addDrinksToDatabase(product: Product){
        customDialogRepository.addDrinksToDatabase(product)
    }

    fun goToDataBase( product:Product){
       customDialogRepository.addProductToDatabase(product)
    }

    open class Factory(val app: Application, private val customDialogRepository: CustomDialogRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CustomDialogViewModel::class.java)) {
                return CustomDialogViewModel(app, customDialogRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}