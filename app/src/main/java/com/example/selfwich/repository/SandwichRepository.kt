package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Product
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class SandwichRepository {
    private val _sandwichList = MutableLiveData<ArrayList<Product>>()
    val sandwichList: LiveData<ArrayList<Product>> = _sandwichList


    private  var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        getAllSandwich()
    }
    fun getAllSandwich() {
        firestore.collection("selfSandwich").addSnapshotListener { docSnapshot, e ->
            if(e != null){
                Log.w(ContentValues.TAG , e.message.toString())
                return@addSnapshotListener
            }
            if (docSnapshot != null){
                val allSandwich=ArrayList<Product>()
                docSnapshot.documents.forEach{
                    val currentSandwich = it.toObject(Product::class.java)
                    it.let {
                        allSandwich.add(currentSandwich!!)
                        _sandwichList.value =allSandwich
                    }
                }
            }
        }


    }
}