package com.example.selfwich.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Order
import com.example.selfwich.model.Singleton
import com.google.firebase.firestore.FirebaseFirestore

class CurrentOrderRepository {
    private  var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val _order: MutableLiveData<Order> = MutableLiveData<Order>()
    val order: LiveData<Order> = _order
    init {
        _order.value=Singleton.globalOrderLive.value
    }
    fun setCurrentOrder(order: Order){
        _order.value= order
    }


}