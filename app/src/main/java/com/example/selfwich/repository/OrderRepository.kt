package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Order
import com.example.selfwich.model.Singleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
enum class OrderStatus { LOADING, READY, CANCELED }
class OrderRepository {
    private val _orderList=  MutableLiveData<ArrayList<Order>>()
    val orderList: LiveData<ArrayList<Order>> = _orderList


    private  var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        getAllOrder()
    }

    private fun getAllOrder() {
        firestore.collection("orders")
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
                            _orderList.value= allOrder
                        }
                    }
                }
            }
    }

    fun orderIsReady(order: Order){
        order.let {
            order.status= OrderStatus.READY.toString()
            val docref=firestore.collection("orders").document(order.orderId)
            docref.set(it).addOnSuccessListener { document->
                if (document!= null){
                    Log.i("order","Databaseye gonderildi ${order.status}")
                } else {
                    Log.i("Click","Document bulamdi" + " ${order.status}")
                }
            }
                .addOnFailureListener { exception->
                    Log.i("Click", "Olmadi yaaa", exception)
                }

        }
        }
    fun orderIsCanceleded(order: Order){
        order.let {
            order.status= OrderStatus.CANCELED.toString()
            val docref=firestore.collection("orders").document(order.orderId)
            docref.set(it).addOnSuccessListener { document->
                if (document!= null){
                    Log.i("order","Databaseye gonderildi ${order.status}")
                } else {
                    Log.i("Click","Document bulamdi Caneldeyiz" + " ${order.status}")
                }
            }
                .addOnFailureListener { exception->
                    Log.i("Click", "Olmadi yaaa", exception)
                }
        }
    }

}


