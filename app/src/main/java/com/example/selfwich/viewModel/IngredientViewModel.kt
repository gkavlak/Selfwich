package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Ingredient
import com.example.selfwich.repository.IngredientRepository

class IngredientViewModel(app: Application , private val ingredientRepository: IngredientRepository) : ViewModel() {

    val addIngredient: LiveData<ArrayList<Ingredient>> = ingredientRepository.addSandwichList
    val ingredientList: LiveData<ArrayList<Ingredient>> = ingredientRepository.ingredientList



        fun publishSandwich(ingredient: Ingredient){
            ingredientRepository.publishSandwich(ingredient)
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