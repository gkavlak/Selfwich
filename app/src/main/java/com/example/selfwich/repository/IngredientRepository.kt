package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Ingredient
import com.example.selfwich.model.Selfwich
import com.google.firebase.firestore.FirebaseFirestore

class IngredientRepository{
    private val _ingredientList=MutableLiveData<ArrayList<Ingredient>>()
    val ingredientList: LiveData<ArrayList<Ingredient>> =_ingredientList


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
    fun writeNewSelfwichToDatabase(selfwich: Selfwich?){
        selfwich?.let {
            val docref=firestore.collection("selfSandwich").document(selfwich?.selfwichName.toString())
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

}