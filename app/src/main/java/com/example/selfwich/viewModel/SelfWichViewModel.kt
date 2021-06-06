package com.example.selfwich.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Product
import com.example.selfwich.model.Selfwich
import com.example.selfwich.repository.SelfWichRepository

class SelfWichViewModel(app : Application, private val selfwichRepository: SelfWichRepository) : ViewModel() {

    val sandwichList: LiveData<ArrayList<Selfwich>> = selfwichRepository.sandwichList

    val isSelfWichLikeAdded:LiveData<Long> = selfwichRepository.isSelfWichLikeAdded

    fun addLikeSelfwichPoint(selfwich: Selfwich){
        selfwichRepository.addLikeToSelfWich(selfwich)
    }



    open class Factory(val app: Application , private val selfwichRepository: SelfWichRepository) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SelfWichViewModel::class.java)) {
                return SelfWichViewModel(app, selfwichRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel is not assignable")
            }
        }
    }
}
