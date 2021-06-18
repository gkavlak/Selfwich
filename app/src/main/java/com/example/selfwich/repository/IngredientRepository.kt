package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

class IngredientRepository{
    private val _ingredientList=MutableLiveData<ArrayList<Ingredient>>()
    val ingredientList: LiveData<ArrayList<Ingredient>> =_ingredientList


    private val _newSelfwich:MutableLiveData<Selfwich?> = MutableLiveData<Selfwich?>(Selfwich())
    val newSelfwich: LiveData<Selfwich?> = _newSelfwich

    private val _totalPrice = MutableLiveData<Long>(0)
    val totalPrice: LiveData<Long> = _totalPrice

    private val _isSuccess= MutableLiveData<Boolean>(false)
    val isSuccess= _isSuccess


    private  var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        getAllIngredient()
        _newSelfwich.value?.userId= Singleton.globalUser.value?.userId.toString()
        _newSelfwich.value?.userName= Singleton.globalUser.value?.userName.toString()
    }

    fun getAllIngredient(){
        firestore.collection("ingredient").addSnapshotListener { docSnapshot, e ->
            if(e != null){
                Log.w(ContentValues.TAG , e.message.toString())
                return@addSnapshotListener
            }
            if (docSnapshot != null){
                val allIngredient=ArrayList<Ingredient>()
                docSnapshot.documents.forEach{
                    val currentIngredient = it.toObject(Ingredient::class.java)
                    it.let {
                        allIngredient.add(currentIngredient!!)
                        _ingredientList.value =allIngredient
                    }
                }
            }
        }

    }
    fun deleteIngredientToDatabase(ingredient : Ingredient){
        firestore.collection("ingredient").document(ingredient.ingredientName)
            .delete()
            .addOnSuccessListener {

                Log.i("delete", "DocumentSnapshot successfully deleted!"+"${it}")  }
            .addOnFailureListener { e ->

                Log.w(ContentValues.TAG, "Error deleting document", e) }

    }
    fun removePurchasePriceToTotalPricee(ingredient: Ingredient){
        _totalPrice.value = totalPrice.value?.minus(ingredient.ingredientPrice)
        _newSelfwich.value?.selfwichPrice = _newSelfwich.value?.selfwichPrice?.minus(ingredient.ingredientPrice)!!
        newSelfwich.value?.selfwichPrice= _newSelfwich.value?.selfwichPrice!!
    }
    fun aaddSelfWichNamee(name:String){
        _newSelfwich.value?.reNameSelfwich(name)
    }
    fun addSelfwichDescc(desc: String){
        _newSelfwich.value?.reDescSelfwich(desc)
    }

    fun addPurchasePriceToTotalPricee(ingredient: Ingredient){
        _totalPrice.value = totalPrice.value?.plus(ingredient.ingredientPrice)
        _newSelfwich.value?.selfwichPrice = _newSelfwich.value?.selfwichPrice?.plus(ingredient.ingredientPrice)!!
        newSelfwich.value?.selfwichPrice= _newSelfwich.value?.selfwichPrice!!
    }
    fun checkifIngredientAddedd(ingredient: Ingredient){
        ingredientList.value?.forEach {
            if(it.ingredientId==ingredient.ingredientId){
                it.isSelected()
            }
        }
    }

    fun addNewSelfwichIngredientt(ingredient: Ingredient){
        var ingredientHave:Boolean =false
        checkifIngredientAddedd(ingredient)
        this._newSelfwich.value?.selfwichIngredients?.forEach {
            ingredientHave=(it.ingredientId == ingredient.ingredientId)
            if (ingredientHave){
                this._newSelfwich.value?.selfwichIngredients?.remove(it)
                this.removePurchasePriceToTotalPricee(it)
                _newSelfwich.value?.calculateTotalSelfwichPrice()
                checkSuccess()
                newSelfwich.value?.selfwichPrice= _newSelfwich.value?.selfwichPrice!!
                Log.i("Click","${newSelfwich.value?.selfwichIngredients} guncellendi")
                Log.i("Click", "t${totalPrice.value}")
                return
            }
        }
        if( !ingredientHave){
            this._newSelfwich.value?.selfwichIngredients?.add(ingredient)
            this.addPurchasePriceToTotalPricee(ingredient)
            _newSelfwich.value?.calculateTotalSelfwichPrice()
            checkSuccess()
            newSelfwich.value?.selfwichPrice= _newSelfwich.value?.selfwichPrice!!
        }
        Log.i("Click","${newSelfwich.value?.selfwichIngredients}yüklendi")
        Log.i("Click", newSelfwich.value?.selfwichName.toString())
    }
    fun checkSuccess(){
        _isSuccess.value = _newSelfwich.value?.selfwichPrice!! > 0
    }


    fun writeNewSelfwichToDatabase(){
        val selfwich = _newSelfwich.value!!
        Singleton.globalOrderLive.value?.selfwichs?.add(selfwich)
        selfwich.let {
            val docref=firestore.collection("selfSandwich").document(selfwich.selfwichName)
            docref.set(it).addOnSuccessListener { document->
                if (document!= null){
                    Log.i("Click","Databaseye gonderildi $selfwich")
                } else {
                    Log.i("Click","Document bulamdi" + " $selfwich")
                }
            }
                .addOnFailureListener { exception->
                    Log.i("Click", "Olmadi yaaa", exception)
                }

        }
    }
    fun addSelfwichToCurrentOrder(){
        val selfwich = _newSelfwich.value!!
        Singleton.globalOrderLive.value?.selfwichs?.add(selfwich)
        Singleton.globalOrderLive.value?.calculatePrice()
    }

}

