package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.repository.CurrentOrderRepository
import com.example.selfwich.repository.DrinksRepository

class CurrentOrderViewModel ( app : Application, private val currentOrderRepository: CurrentOrderRepository ): ViewModel() {




    open class Factory(val app: Application, private val currentOrderRepository: CurrentOrderRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CurrentOrderViewModel::class.java)) {
                return CurrentOrderViewModel(app, currentOrderRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}