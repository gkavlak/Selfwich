package com.example.selfwich.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Product

class DrinksRepository {
    private val _drinksList = MutableLiveData<ArrayList<Product>>()
    val drinksList: LiveData<ArrayList<Product>> = _drinksList
}