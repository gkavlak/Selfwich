package com.example.selfwich.viewModel

import android.app.Application
import android.app.ApplicationErrorReport
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Product
import com.example.selfwich.repository.DrinksRepository
import com.example.selfwich.repository.IngredientRepository

class IngredientViewModel(app: Application , private val ingredientRepository: IngredientRepository) : ViewModel() {

    val addIngredient: LiveData<ArrayList<Product>> = ingredientRepository.addSandwichList
    val ingredientList: LiveData<ArrayList<Product>> = ingredientRepository.ingredientList



        fun publishSandwich(product: Product){
            ingredientRepository.publishSandwich(product)
        }



    open class Factory(val app: Application, private val ingredientRepository: IngredientRepository) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(IngredientViewModel::class.java)) {
                return IngredientViewModel(app, ingredientRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}