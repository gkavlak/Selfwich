package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Product
import com.example.selfwich.repository.DrinksRepository
import com.example.selfwich.repository.EatsRepository

class EatsViewModel(app : Application , private val eatsRepository: EatsRepository) : ViewModel() {
    val eatsList: LiveData<ArrayList<Product>> = eatsRepository.eatsList

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