package com.example.selfwich.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Product

class SandwichRepository {
    private val _sandwichList = MutableLiveData<ArrayList<Product>>()
    val sandwichlist: LiveData<ArrayList<Product>> = _sandwichList
}