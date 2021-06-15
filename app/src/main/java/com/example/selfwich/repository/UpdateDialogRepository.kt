package com.example.selfwich.repository

import android.util.Log
import com.example.selfwich.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class UpdateDialogRepository (){
    private lateinit var firestore: FirebaseFirestore
    init {
        firestore = FirebaseFirestore.getInstance()
    }

    fun updateDrinks(product: Product) {
        product?.let {
            val docref= firestore.collection("drinks")
                .document("lemonade")
            docref.update("pName", product.pName).addOnSuccessListener { document->
                if (document!= null){
                    Log.i("Click","Databaseye gonderildi $product")

                } else {
                    Log.i("Click","Document bulamdi" + " $product")
                }

            }
                .addOnFailureListener { exception->
                    Log.i("Click", "Olmadi yaaa", exception)
                }
        }
    }
}