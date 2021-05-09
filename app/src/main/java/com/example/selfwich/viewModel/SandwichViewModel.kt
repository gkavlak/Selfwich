package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Product
import com.example.selfwich.repository.SandwichRepository

class SandwichViewModel(app : Application , private val sandwichRepository: SandwichRepository) : ViewModel() {

    val sandwichList: LiveData<ArrayList<Product>> = sandwichRepository.sandwichList
    fun addSelfSandwich(product: Product){
        sandwichRepository.addSelfSandwich(product)
    }

    open class Factory(val app: Application , private val sandwichRepository: SandwichRepository) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SandwichViewModel::class.java)) {
                return SandwichViewModel(app, sandwichRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}
