package com.example.selfwich.repository

import android.content.ContentValues
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
    fun refreshOrder(){
        firestore.collection("orders").document(_order.value?.orderId!!)
            .get().addOnSuccessListener {
                if (it != null){
                    _order.value=it.toObject(Order::class.java)
                }

            }
    }
    fun deleteProductToDatabase(product:Product){
         _order.value?.products?.remove(product)
        _order.value?.calculatePrice()
        refreshOrder()

        firestore.collection("orders").document(_order.value?.orderId!!)
            .set(_order.value!!)
            .addOnSuccessListener {

                Log.i("delete", "DocumentSnapshot successfully deleted!"+"${it}")  }
            .addOnFailureListener { e ->

                Log.w(ContentValues.TAG, "Error deleting document", e) }

    }
}