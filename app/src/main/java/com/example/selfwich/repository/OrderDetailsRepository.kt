package com.example.selfwich.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Order
import com.example.selfwich.model.Product
import java.util.ArrayList

class OrderDetailsRepository {


    private  var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val _order: MutableLiveData<Order> = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    fun getOrder(orderId: String){
        firestore.collection("orders").document(orderId).addSnapshotListener{docSnaphot,e->
            if (e!=null){
                Log.w(TAG,e.message.toString())
                return@addSnapshotListener
            }
            if(docSnaphot != null){
                _order.value=docSnaphot.toObject(Order::class.java)
            }
        }

        return
    }
}