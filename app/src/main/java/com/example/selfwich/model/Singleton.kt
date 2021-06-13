package com.example.selfwich.model

import androidx.lifecycle.MutableLiveData

object Singleton
{
    init {
        println("Singleton class invoked.")
    }
    var globalUser:MutableLiveData<DomainUser> = MutableLiveData<DomainUser>(DomainUser())
    var globalOrder:Order= Order()
    var globalOrderLive:MutableLiveData<Order> = MutableLiveData<Order>(Order())

}

