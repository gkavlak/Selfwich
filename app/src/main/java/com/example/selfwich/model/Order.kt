package com.example.selfwich.model

import java.util.*

data class Order(
     var orderId:String= UUID.randomUUID().toString(),
     var date: Date=Calendar.getInstance().time,
     var ownerId: String="",
     var name : String= "",
     var orderPrice:Long = 0,
     var selfwichs: MutableList<Selfwich> = mutableListOf(),
     var products:MutableList<Product> = mutableListOf()
) {
     fun calculatePrice(){
          orderPrice=0
          selfwichs.forEach { selfwich ->
               orderPrice += selfwich.selfwichPrice
          }
          products.forEach { product ->
               orderPrice += product.pPrice
          }
     }
}