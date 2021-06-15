package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.repository.OrderDetailsRepository
import com.example.selfwich.repository.OrderRepository

class OrderDetailsViewModel(val app: Application, orderDetailsRepository: OrderDetailsRepository) : ViewModel(){




    open class Factory(val app: Application, private val orderDetailsRepository: OrderDetailsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderDetailsViewModel::class.java)) {
                return OrderDetailsViewModel(app, orderDetailsRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }

}
}