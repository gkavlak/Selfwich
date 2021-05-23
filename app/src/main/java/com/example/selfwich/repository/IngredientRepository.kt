package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.FirebaseDataBase
import com.example.selfwich.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class IngredientRepository{
    private val _ingredientList=MutableLiveData<ArrayList<Product>>()
    val ingredientList: LiveData<ArrayList<Product>> =_ingredientList

    private val _addSandwichList = MutableLiveData<ArrayList<Product>>()
    val addSandwichList: LiveData<ArrayList<Product>> = _addSandwichList

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
                val allIngredient=ArrayList<Product>()
                docSnapshot.documents.forEach{
                    val currentIngredient = it.toObject(Product::class.java)
                    it.let {
                        allIngredient.add(currentIngredient!!)
                        _ingredientList.value =allIngredient
                    }
                }
            }
        }

    }
        fun publishSandwich(product: Product){
            val data = ArrayList<Product>()
               val desc = hashMapOf(
                  "pName" to product.pName,
                    "pDesc" to product.pDesc
               )
            val docref=firestore.collection("selfSandwich")
                    .document(product.pName)
            docref.set(desc)
                    .addOnSuccessListener {document->
                        if (document!=null){
                            Log.d("exist","DocumentSnaphotData")
                            val allProduct= ArrayList<Product>()
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