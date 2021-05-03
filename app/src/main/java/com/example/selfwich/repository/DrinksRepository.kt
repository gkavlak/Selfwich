package com.example.selfwich.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Product
import com.google.firebase.firestore.FirebaseFirestore
const val TAG="ShopRepository"
class DrinksRepository {
    private val _drinksList = MutableLiveData<ArrayList<Product>>()
    val drinksList: LiveData<ArrayList<Product>> = _drinksList
    private  var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        getAllDrinks()
    }
    fun getAllDrinks() {

            firestore.collection("drinks").addSnapshotListener { docSnapshot, e ->
                //Exception hata var
                if (e != null) {
                    Log.w(TAG, e.message.toString())
                    return@addSnapshotListener
                }
                //Exception olmadigi icin document vardir
                if (docSnapshot != null) {
                    //snapshoti gercekleyecegiz
                    val allProduct = ArrayList<Product>()
                    docSnapshot.documents.forEach {
                        val currentProduct = it.toObject(Product::class.java)

                        it?.let {
                            allProduct.add(currentProduct!!)
                        }
                    }
                    _drinksList.value = allProduct
                }
            }
    }
}