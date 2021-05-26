package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Ingredient
import com.google.firebase.firestore.FirebaseFirestore

class IngredientRepository{
    private val _ingredientList=MutableLiveData<ArrayList<Ingredient>>()
    val ingredientList: LiveData<ArrayList<Ingredient>> =_ingredientList

    private val _addSandwichList = MutableLiveData<ArrayList<Ingredient>>()
    val addSandwichList: LiveData<ArrayList<Ingredient>> = _addSandwichList

    private  var firestore: FirebaseFirestore
    init {

        firestore = FirebaseFirestore.getInstance()
        getAllIngredient()
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
        fun publishSandwich(ingredient: Ingredient){
            val data = ArrayList<Ingredient>()
               val desc = hashMapOf(
                  "pName" to ingredient.pName,
                    "pDesc" to ingredient.pDesc
               )
            val docref=firestore.collection("selfSandwich")
                    .document(ingredient.pName)
            docref.set(desc)
                    .addOnSuccessListener {document->
                        if (document!=null){
                            Log.d("exist","DocumentSnaphotData")
                            val allProduct= ArrayList<Ingredient>()
                            _addSandwichList.value=allProduct
                        }
                        else{
                            Log.d("no exist","No such Document")
                        }
                    }
                    .addOnFailureListener{ exception->
                        Log.d("errordb","get failed with", exception)
                    }
        }
}