package com.example.selfwich.repository

import android.util.Log
import com.example.selfwich.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.concurrent.fixedRateTimer

class UpdateDialogRepository (){
    private lateinit var firestore: FirebaseFirestore
    init {
        firestore = FirebaseFirestore.getInstance()
    }

    fun updateDrinks(oldproduct: Product, newproduct:Product) {
            val deletedoc= firestore.collection("drinks").document(oldproduct.pName)
            deletedoc.delete().addOnSuccessListener {
                if (it != null){
                    Log.i("update","old product silindi  ${it} ${oldproduct}")
                }else{
                    Log.i("update","old product silindi  ${oldproduct}")
                }
            }.addOnFailureListener {
                Log.i("update","product silinemedi  ${oldproduct}", it)

            }

        val docref= firestore.collection("drinks")
            .document(newproduct.pName)
        docref.set(newproduct).addOnSuccessListener { document->
            if (document!= null){
                Log.i("Click","Databaseye gonderildi $oldproduct")

            } else {
                Log.i("Click","Document bulamdi" + " $oldproduct")
            }

        }
            .addOnFailureListener { exception->
                Log.i("Click", "Olmadi yaaa", exception)
            }
    }
}