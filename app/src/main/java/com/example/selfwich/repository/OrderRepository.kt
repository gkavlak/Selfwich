package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class OrderRepository {
    private val _orderList=  MutableLiveData<ArrayList<Order>>()
    val orderList: LiveData<ArrayList<Order>> = _orderList

    private  var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        getAllOrder()

    }

    private fun getAllOrder() {


        firestore.collection("order")
            .addSnapshotListener{docSnapshot, e ->
                if (e != null ){
                    Log.w(ContentValues.TAG , e.message.toString())
                    return@addSnapshotListener
                }
                if (docSnapshot != null){
                    val allOrder= ArrayList<Order>()
                    docSnapshot.documents.forEach {
                        val currentOrder = it.toObject(Order::class.java)
                        it.let {
                            allOrder.add(currentOrder!!)
                            _orderList.value=allOrder
                        }
                    }
                }

            }

    }

}