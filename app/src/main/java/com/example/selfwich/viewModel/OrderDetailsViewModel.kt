package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.repository.OrderDetailsRepository
import com.example.selfwich.repository.OrderRepository
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Order
import com.example.selfwich.model.Product
import com.example.selfwich.model.Selfwich
import java.util.ArrayList

class OrderDetailsViewModel(
    val orderId: String,
    val app: Application,
    private val orderDetailsRepository: OrderDetailsRepository
) : ViewModel() {

    val order: LiveData<Order> = orderDetailsRepository.order


    init {
        orderDetailsRepository.getOrder(orderId)
    }

    fun deleteProductInOrder(product: Product) {
        orderDetailsRepository.deleteProductToDatabase(product)
    }

    fun deleteSelfwichInOrder(selfwich: Selfwich) {
        orderDetailsRepository.deleteSelfwichToDatabase(selfwich)
    }
    fun readyforOder(){
        orderDetailsRepository.readyToorder()
    }
    fun cancelForOrder(){
        orderDetailsRepository.canceledOrder()
    }

    open class Factory(
        val orderId: String,
        val app: Application,
        private val orderDetailsRepository: OrderDetailsRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderDetailsViewModel::class.java)) {
                return OrderDetailsViewModel(orderId, app, orderDetailsRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}
