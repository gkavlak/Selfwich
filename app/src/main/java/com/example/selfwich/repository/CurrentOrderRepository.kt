package com.example.selfwich.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Order
import com.example.selfwich.model.Singleton
import com.google.firebase.firestore.FirebaseFirestore

class CurrentOrderRepository {
    private  var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _order: MutableLiveData<Order> = MutableLiveData<Order>()
    val order: LiveData<Order> = _order
    init {
        _order.value=Singleton.globalOrderLive.value
    }
    fun setCurrentOrder(order: Order){
        _order.value= order
    }
    fun writeOrdertoDataBase(order: Order){

        order.let {
            val docref=firestore.collection("orders").document(order.orderId)
            docref.set(it).addOnSuccessListener {  document->
                resetSingletonOrder()
                refreshCurrentOrder()
                if (document!= null){
                    Log.i("order","Databaseye gonderildi $order")
                } else {
                    Log.i("order","Document bulamdi" + " $order")
                }
            }
                .addOnFailureListener { exception->
                    Log.i("order", "Olmadi yaaa", exception)
                }

        }
    }
    fun resetSingletonOrder(){
        val newOrderLive:MutableLiveData<Order> = MutableLiveData<Order>(Order())
        Singleton.globalOrderLive= newOrderLive
    }
    fun refreshCurrentOrder() {
        _order.value = Singleton.globalOrderLive.value

    }


}