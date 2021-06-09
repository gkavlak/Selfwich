package com.example.selfwich.model

import java.util.*

data class Order(
     var orderId:Long=0,
     var date: Date=Calendar.getInstance().time,
     var ownerId: String="",
     var name : String= "",
     var SelfwichOrder: MutableList<Selfwich> = mutableListOf(),
     var ProductOrder:MutableList<Product> = mutableListOf()
) {
}