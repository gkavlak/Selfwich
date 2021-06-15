package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Order
import com.example.selfwich.repository.OrderRepository

class OrderViewModel
    (val app:Application,  private val orderRepository: OrderRepository): ViewModel() {

    val orderlist :LiveData<ArrayList<Order>> = orderRepository.orderList

    fun orderIsReady(order: Order){
        orderRepository.orderIsReady(order)
    }
    fun orderISCanceled(order: Order){
        orderRepository.orderIsCanceleded(order)
    }


    open class Factory(val app: Application, private val orderRepository: OrderRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
                return OrderViewModel(app, orderRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}