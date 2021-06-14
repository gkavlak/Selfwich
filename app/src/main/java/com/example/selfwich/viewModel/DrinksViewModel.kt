package com.example.selfwich.viewModel

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

class DrinksViewModel(app :Application,private val drinksRepository: DrinksRepository) : ViewModel(){

    val drinkList: LiveData<ArrayList<Product>> = drinksRepository.drinksList
    val isLikeAdded:LiveData<Long> = drinksRepository.isLikeAdded

    val isDrinksDelete:LiveData<Long> = drinksRepository.isDrinksDelete

    fun deleteProduct(product:Product){
        drinksRepository.deleteProductToDatabase(product)
    }


    fun addLikePoint(product: Product){
        drinksRepository.addLikeToDrink(product)
    }
    fun addProducttoOrder(product: Product){
        Singleton.globalOrder.products.add(product)
        Singleton.globalOrder.calculatePrice()
        Singleton.globalOrderLive.value?.products?.add(product)
        Singleton.globalOrderLive.value?.calculatePrice()
        Log.i("buy", "t${Singleton.globalOrderLive.value?.products!!}")
        Log.i("buy", "t${Singleton.globalOrderLive.value?.orderPrice!!}")
    }



    open class Factory(val app: Application, private val drinksRepository: DrinksRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DrinksViewModel::class.java)) {
                return DrinksViewModel(app, drinksRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}