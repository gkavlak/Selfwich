package com.example.selfwich.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Ingredient
import com.example.selfwich.repository.IngredientRepository

class IngredientViewModel(app: Application , private val ingredientRepository: IngredientRepository) : ViewModel() {

    val addIngredient: LiveData<ArrayList<Ingredient>> = ingredientRepository.addSandwichList

    val ingredientList: LiveData<ArrayList<Ingredient>> = ingredientRepository.ingredientList

    private val _NewSelfwiuchingredientList=MutableLiveData<ArrayList<Ingredient>>()
    val NewSelfwiuchIngredientList: LiveData<ArrayList<Ingredient>> =_NewSelfwiuchingredientList
    private val allIngredient=ArrayList<Ingredient>()
    private val totalPrice = MutableLiveData<Long>(0)

    fun addPurchasePriceToTotalPrice(ingredient: Ingredient){
       totalPrice.value = totalPrice.value?.plus(ingredient.ingredientPrice)
    }
    fun removePurchasePriceToTotalPrice(ingredient: Ingredient){
        totalPrice.value = totalPrice.value?.minus(ingredient.ingredientPrice)
    }

    fun addNewSelfwichIngredient(ingredient: Ingredient){
        var ingredientHave:Boolean =false

        allIngredient.forEach {

            ingredientHave=(it.ingredientId == ingredient.ingredientId)
            if (ingredientHave){
                allIngredient.remove(it)
                this.removePurchasePriceToTotalPrice(it)
                _NewSelfwiuchingredientList.value=allIngredient
                Log.i("Click","$allIngredient silindi")
                Log.i("Click",totalPrice.value.toString())
                return}
        }
        if( !ingredientHave){
            allIngredient.add(ingredient)
            this.addPurchasePriceToTotalPrice(ingredient)
            Log.i("Click","$allIngredient yüklendi")
        }

        _NewSelfwiuchingredientList.value=allIngredient
        Log.i("Click",totalPrice.value.toString())


    }




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