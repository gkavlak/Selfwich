package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Product
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.ArrayList

class EatsRepository {
    private val _eatsList= MutableLiveData<ArrayList<Product>>()
    val eatsList:LiveData<ArrayList<Product>> =_eatsList
    private val _isLikeAdded=MutableLiveData<Long>()
    val isLikeAdded:LiveData<Long> = _isLikeAdded



    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        getAllEats()
    }
    fun getAllEats(){
         firestore.collection("eats").addSnapshotListener{docSnaphot,e->
          if (e!=null){
                Log.w(TAG,e.message.toString())
              return@addSnapshotListener
          }
             if(docSnaphot!=null){
                 val allProduct =ArrayList<Product>()
                 docSnaphot.documents.forEach {
                    val currentEats=it.toObject(Product::class.java)
                     it?.let {
                         allProduct.add(currentEats!!)
                     }
                 }
                 _eatsList.value=allProduct
             }
        }

    }
    fun addLikeToDrink(product:Product){
        val plus: Long = 1
        firestore.collection("eats")
                .document(product.pName)
                .update("pLike", FieldValue.increment(plus))
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG , "DocumentSnapshot successfully written!")
                    _isLikeAdded.value = product.pLike
                }
                .addOnFailureListener{ e->Log.d(ContentValues.TAG , "DocumentSnapshot ${e.message}!") }

    }
    fun deleteProductToDatabase(product:Product){
        firestore.collection("eats").document(product.pName)
            .delete()
            .addOnSuccessListener {


                Log.i("delete", "DocumentSnapshot successfully deleted!"+"${it}")  }
            .addOnFailureListener { e ->

                Log.w(ContentValues.TAG, "Error deleting document", e) }

    }

}