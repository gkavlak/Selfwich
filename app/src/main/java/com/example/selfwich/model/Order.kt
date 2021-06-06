package com.example.selfwich.model

import java.util.*

data class Order(
     var orderId:Long,
     var date: Date,
     var ownerId: Long,
     var name : String= "",
     var SelfwichOrder: MutableList<Selfwich> = mutableListOf(),
     var ProductOrder:MutableList<Product> = mutableListOf()
) {
}