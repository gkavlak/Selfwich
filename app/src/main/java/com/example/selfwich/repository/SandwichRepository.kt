package com.example.selfwich.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class SandwichRepository {
    private val _sandwichList = MutableLiveData<ArrayList<Product>>()
    val sandwichList: LiveData<ArrayList<Product>> = _sandwichList
    private  var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()

    }
     fun addSelfSandwich(product: Product){
         val data = ArrayList<Product>()
         val content=ArrayList<Product>()

         data.add(product)
         val docref=firestore.collection("selfSandwich")
                 .document(product.pName)
         docref.set(product.pName to content)
                 .addOnSuccessListener {document->
                     if (document!=null){
                         Log.d("exist","DocumentSnaphotData")
                         val allProduct= ArrayList<Product>()
                         _sandwichList.value=allProduct
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