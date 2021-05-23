package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Product
import com.example.selfwich.repository.DrinksRepository

class DrinksViewModel(app :Application,private val drinksRepository: DrinksRepository) : ViewModel(){

    val drinkList: LiveData<ArrayList<Product>> = drinksRepository.drinksList
    val isLikeAdded:LiveData<Long> = drinksRepository.isLikeAdded

    fun addLikePoint(product: Product){
        drinksRepository.addLikeToDrink(product)
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