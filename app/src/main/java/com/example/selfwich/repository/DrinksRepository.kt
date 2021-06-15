package com.example.selfwich.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
const val TAG="ShopRepository"
class DrinksRepository {
    private val _drinksList = MutableLiveData<ArrayList<Product>>()
    val drinksList: LiveData<ArrayList<Product>> = _drinksList

    private val _isLikeAdded=MutableLiveData<Long>()
    val isLikeAdded:LiveData<Long> = _isLikeAdded



    private  var firestore: FirebaseFirestore
    //fun getCurrentLike(): DocumentReference? = firestore.collection("drinks").parent

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
    fun addLikeToDrink(product:Product){
        val plus: Long = 1
        firestore.collection("drinks")
                .document(product.pName)
                .update("pLike", FieldValue.increment(plus))
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    _isLikeAdded.value = product.pLike
                }
                .addOnFailureListener{ e->Log.d(TAG, "DocumentSnapshot ${e.message}!") }

    }
    fun deleteProductToDatabase(product:Product){
        firestore.collection("drinks").document(product.pName)
            .delete()
            .addOnSuccessListener {

                Log.i("delete", "DocumentSnapshot successfully deleted!"+"${it}") }
            .addOnFailureListener { e ->

                Log.w(TAG, "Error deleting document", e) }

    }
}