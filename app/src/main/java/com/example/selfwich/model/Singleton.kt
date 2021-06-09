package com.example.selfwich.model

object Singleton
{
    init {
        println("Singleton class invoked.")
    }
    var globalUser:DomainUser = DomainUser()
    var globalOrder:Order= Order()

}

