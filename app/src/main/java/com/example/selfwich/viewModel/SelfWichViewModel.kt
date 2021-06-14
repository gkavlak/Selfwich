package com.example.selfwich.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.model.Selfwich
import com.example.selfwich.model.Singleton
import com.example.selfwich.repository.SelfWichRepository

class SelfWichViewModel(app : Application, private val selfwichRepository: SelfWichRepository) : ViewModel() {

    val sandwichList: LiveData<ArrayList<Selfwich>> = selfwichRepository.sandwichList

    val isSelfWichLikeAdded:LiveData<Long> = selfwichRepository.isSelfWichLikeAdded

    fun addLikeSelfwichPoint(selfwich: Selfwich){
        selfwichRepository.addLikeToSelfWich(selfwich)
    }
    fun deleteSelwichToDatabase(selfwich: Selfwich){
        selfwichRepository.deleteSelfwichToDatabase(selfwich)
    }
    fun addSelfwichtoOrder(selfwich: Selfwich){
        Singleton.globalOrderLive.value?.selfwichs?.add(selfwich)
        Singleton.globalOrderLive.value?.calculatePrice()
        Singleton.globalOrder.selfwichs.add(selfwich)
        Singleton.globalOrder.calculatePrice()
        Log.i("buy", "t${Singleton.globalOrderLive.value?.selfwichs!!}")
        Log.i("buy", "t${Singleton.globalOrderLive.value?.orderPrice!!}")
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
