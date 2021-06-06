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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.selfwich.model.FirebaseDataBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

class IngredientViewModel(app: Application , private val ingredientRepository: IngredientRepository) : ViewModel() {

    private val _ingredientList:MutableLiveData<ArrayList<Ingredient>> = ingredientRepository.ingredientList as MutableLiveData<ArrayList<Ingredient>>
    val ingredientList: LiveData<ArrayList<Ingredient>> =_ingredientList
    private val _newSelfwich:MutableLiveData<Selfwich?> = MutableLiveData<Selfwich?>(Selfwich())
    val newSelfwich: LiveData<Selfwich?> = _newSelfwich


    private val _currentAuthUser = MutableLiveData<Selfwich>()
    val currentAutUser : LiveData<Selfwich> = _currentAuthUser
    private val _totalPrice = MutableLiveData<Long>(0)
    val totalPrice: LiveData<Long> = _totalPrice

   private val firebaserepo=FirebaseDataBase()

    init {
        val userId =Firebase.auth.currentUser.uid
        firebaserepo.userIdToName(userId)
        _newSelfwich.value?.userId = userId!!

    }

    fun addPurchasePriceToTotalPrice(ingredient: Ingredient){
        _totalPrice.value = totalPrice.value?.plus(ingredient.ingredientPrice)
        _newSelfwich.value?.selfwichPrice = _newSelfwich.value?.selfwichPrice?.plus(ingredient.ingredientPrice)!!
    }
    fun removePurchasePriceToTotalPrice(ingredient: Ingredient){
        _totalPrice.value = totalPrice.value?.minus(ingredient.ingredientPrice)
        _newSelfwich.value?.selfwichPrice = _newSelfwich.value?.selfwichPrice?.minus(ingredient.ingredientPrice)!!
    }
    fun aaddSelfWichName(name:String){
        _newSelfwich.value?.reNameSelfwich(name)
    }
    fun addSelfwichDesc(name: String){
        _newSelfwich.value?.reDescSelfwich(name)
    }
    fun goToDataBase(){
        ingredientRepository.writeNewSelfwichToDatabase(newSelfwich.value)
    }
    fun checkifIngredientAdded(ingredient: Ingredient){
        ingredientList.value?.forEach {
            if(it.ingredientId==ingredient.ingredientId){
            it.isSelected()
            }
        }
    }

    fun addNewSelfwichIngredient(ingredient: Ingredient){
        var ingredientHave:Boolean =false
        checkifIngredientAdded(ingredient)
        this._newSelfwich.value?.selfwichIngredients?.forEach {
            ingredientHave=(it.ingredientId == ingredient.ingredientId)
            if (ingredientHave){
                this._newSelfwich.value?.selfwichIngredients?.remove(it)
                this.removePurchasePriceToTotalPrice(it)
                _newSelfwich.value?.calculateTotalSelfwichPrice()
                Log.i("Click","${newSelfwich.value?.selfwichIngredients} guncellendi")
                Log.i("Click", "t${totalPrice.value}")
                return
            }
        }
        if( !ingredientHave){
            this._newSelfwich.value?.selfwichIngredients?.add(ingredient)
            this.addPurchasePriceToTotalPrice(ingredient)
            _newSelfwich.value?.calculateTotalSelfwichPrice()
        }
        Log.i("Click","${newSelfwich.value?.selfwichIngredients}yüklendi")
        Log.i("Click", newSelfwich.value?.selfwichName.toString())
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