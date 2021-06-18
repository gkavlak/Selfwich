package com.example.selfwich.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.*
import com.example.selfwich.repository.IngredientRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

class IngredientViewModel(app: Application , private val ingredientRepository: IngredientRepository) : ViewModel() {

    val ingredientList: LiveData<ArrayList<Ingredient>> = ingredientRepository.ingredientList

    val newSelfwich: LiveData<Selfwich?> = ingredientRepository.newSelfwich

    val isSuccess :LiveData<Boolean> = ingredientRepository.isSuccess

    val totalPrice: LiveData<Long> = ingredientRepository.totalPrice

    fun deleteIngredient(ingredient: Ingredient){
        ingredientRepository.deleteIngredientToDatabase(ingredient)
    }
    fun toOrder(){
        ingredientRepository.addSelfwichToCurrentOrder()
    }

    fun aaddSelfWichName(name:String){
        ingredientRepository.aaddSelfWichNamee(name)
    }
    fun addSelfwichDesc(desc: String){
        ingredientRepository.addSelfwichDescc(desc)
    }
    fun goToDataBase(){
        ingredientRepository.writeNewSelfwichToDatabase()
    }

    fun checkifIngredientAdded(ingredient: Ingredient){
        ingredientRepository.checkifIngredientAddedd(ingredient)
    }

    fun addNewSelfwichIngredient(ingredient: Ingredient){
        ingredientRepository.addNewSelfwichIngredientt(ingredient)
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