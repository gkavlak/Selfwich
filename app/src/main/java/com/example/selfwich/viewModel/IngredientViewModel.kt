package com.example.selfwich.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Ingredient
import com.example.selfwich.model.Selfwich
import com.example.selfwich.repository.IngredientRepository

class IngredientViewModel(app: Application , private val ingredientRepository: IngredientRepository) : ViewModel() {

    val addIngredient: LiveData<ArrayList<Ingredient>> = ingredientRepository.addSandwichList

    val ingredientList: LiveData<ArrayList<Ingredient>> = ingredientRepository.ingredientList

    private val _NewSelfwiuchingredientList=MutableLiveData<ArrayList<Ingredient>>()
    val NewSelfwiuchIngredientList: LiveData<ArrayList<Ingredient>> =_NewSelfwiuchingredientList

    private val _newSelfwich:MutableLiveData<Selfwich?> = MutableLiveData<Selfwich?>(Selfwich())
    val newSelfwich: LiveData<Selfwich?> = _newSelfwich



    private val _allIngredient=ArrayList<Ingredient>()
    val allIngredient:ArrayList<Ingredient> = _allIngredient


    private val _totalPrice = MutableLiveData<Long>(0)
    val totalPrice: LiveData<Long> = _totalPrice


    fun addPurchasePriceToTotalPrice(ingredient: Ingredient){
        _totalPrice.value = _totalPrice.value?.plus(ingredient.ingredientPrice)
        _newSelfwich.value?.selfwichPrice?.plus(ingredient.ingredientPrice)
    }
    fun removePurchasePriceToTotalPrice(ingredient: Ingredient){
        _totalPrice.value = _totalPrice.value?.minus(ingredient.ingredientPrice)
        _newSelfwich.value?.selfwichPrice?.minus(ingredient.ingredientPrice)
    }
    fun aaddSelfWichName(name:String){
        _newSelfwich.value?.reNameSelfwich(name)
    }
    fun goToDataBase(){
        ingredientRepository.writeNewSelfwichToDatabase(newSelfwich?.value)

    }



    fun addNewSelfwichIngredient(ingredient: Ingredient){
        var ingredientHave:Boolean =false

        this._newSelfwich.value?.selfwichIngredients?.forEach {

            ingredientHave=(it.ingredientId == ingredient.ingredientId)
            if (ingredientHave){
                this._newSelfwich.value?.selfwichIngredients?.remove(it)
                this.removePurchasePriceToTotalPrice(it)
                _newSelfwich.value?.calculateTotalSelfwichPrice()
                Log.i("Click","${newSelfwich.value?.selfwichIngredients} guncellendi")
                Log.i("Click",newSelfwich.value?.selfwichPrice.toString())
                return
            }
        }
        if( !ingredientHave){
            this._newSelfwich.value?.selfwichIngredients?.add(ingredient)
            this.addPurchasePriceToTotalPrice(ingredient)
            _newSelfwich.value?.calculateTotalSelfwichPrice()

        }
        Log.i("Click","${newSelfwich.value?.selfwichIngredients}yüklendi")
        Log.i("Click",newSelfwich.value?.selfwichPrice.toString())
        Log.i("Click", newSelfwich.value?.selfwichName.toString())



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