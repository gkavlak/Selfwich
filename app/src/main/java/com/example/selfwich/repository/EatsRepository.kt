package com.example.selfwich.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.ArrayList

class EatsRepository {
    private val _eatsList= MutableLiveData<ArrayList<Product>>()
    val eatsList:LiveData<ArrayList<Product>> =_eatsList
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

}