package com.example.selfwich.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Order
import com.example.selfwich.model.Product
import com.example.selfwich.model.Singleton
import com.example.selfwich.repository.DrinksRepository
import com.example.selfwich.repository.EatsRepository

class EatsViewModel(app : Application , private val eatsRepository: EatsRepository) : ViewModel() {
    val eatsList: LiveData<ArrayList<Product>> = eatsRepository.eatsList
    val isLikeAdded:LiveData<Long> = eatsRepository.isLikeAdded
    val newOrder:MutableLiveData<Order> = MutableLiveData<Order>(Order())


    fun addLikePoint(product: Product){
        eatsRepository.addLikeToDrink(product)
    }
    fun addProducttoOrder(product: Product){
        Singleton.globalOrder.products.add(product)
        newOrder.value?.products?.add(product)
        Singleton.globalOrder.calculatePrice()
        newOrder.value?.calculatePrice()
        Log.i("Buy", "${Singleton.globalOrder}")
        Log.i("Buy", "${newOrder.value?.products}")
    }

    open class Factory(val app: Application, private val eatsRepository: EatsRepository) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EatsViewModel::class.java)) {
                return EatsViewModel(app, eatsRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}