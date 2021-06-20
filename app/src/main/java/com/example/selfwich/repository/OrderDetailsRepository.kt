package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Order
import com.example.selfwich.model.Product
import com.example.selfwich.model.Selfwich
import java.util.ArrayList

class OrderDetailsRepository {


    private  var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _productListIOrder=  MutableLiveData<ArrayList<Product>>()
    val productListIOrder: LiveData<ArrayList<Product>> = _productListIOrder

    private val _selfwichListIOrder=  MutableLiveData<ArrayList<Product>>()
    val selfwichListIOrderr: LiveData<ArrayList<Product>> = _selfwichListIOrder

    val _order: MutableLiveData<Order> = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    init {
        _productListIOrder.value = _order.value?.products as ArrayList<Product>?
        _selfwichListIOrder.value = _order.value?.products as ArrayList<Product>?
    }
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
    fun readyToorder(){

        _order.value?.let {
            it.status= OrderStatus.READY.toString()
            val docref=firestore.collection("orders").document(it.orderId)
            docref.set(it).addOnSuccessListener { document->

                if (document!= null){
                    Log.i("order","Databaseye gonderildi ${it.status}")
                } else {
                    Log.i("Click","Document bulamdi" + " ${it.status}")
                }
            }
                .addOnFailureListener { exception->
                    Log.i("Click", "Olmadi yaaa", exception)
                }

        }

    }
    fun canceledOrder(){
        _order.value?.let {
            it.status= OrderStatus.CANCELED.toString()
            val docref=firestore.collection("orders").document(it.orderId)
            docref.set(it).addOnSuccessListener { document->

                if (document!= null){
                    Log.i("order","Databaseye gonderildi ")
                } else {
                    Log.i("order","Document bulamdi Caneldeyiz" )
                }
            }
                .addOnFailureListener { exception->
                    Log.i("Click", "Olmadi yaaa", exception)
                }
        }

    }
    fun refreshOrder(){
        firestore.collection("orders").document(_order.value?.orderId!!)
            .get().addOnSuccessListener {
                if (it != null){
                    _order.value=it.toObject(Order::class.java)
                    _productListIOrder.value = _order.value?.products as ArrayList<Product>?
                    _selfwichListIOrder.value = _order.value?.products as ArrayList<Product>?
                }

            }
    }
    fun deleteProductToDatabase(product:Product){
        _order.value?.products?.remove(product)
        _order.value?.calculatePrice()
        firestore.collection("orders").document(_order.value?.orderId!!)
            .set(_order.value!!)
            .addOnSuccessListener {
                refreshOrder()


                Log.i("delete", "DocumentSnapshot successfully deleted!"+"${it}")  }
            .addOnFailureListener { e ->

                Log.w(ContentValues.TAG, "Error deleting document", e) }

    }
    fun deleteSelfwichToDatabase(selfwich: Selfwich){
        _order.value?.selfwichs?.remove(selfwich)
        _order.value?.calculatePrice()
        firestore.collection("orders").document(_order.value?.orderId!!)
            .set(_order.value!!)
            .addOnSuccessListener {
                refreshOrder()

                Log.i("delete", "DocumentSnapshot successfully deleted!"+"${it}")  }
            .addOnFailureListener { e ->

                Log.w(ContentValues.TAG, "Error deleting document", e) }

    }
}