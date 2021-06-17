package com.example.selfwich.model



import java.text.SimpleDateFormat
import java.util.*


data class Order(
     var orderId: String = UUID.randomUUID().toString(),
     var date: Date = Calendar.getInstance().time,
     var ownerId: String = "",
     var status: String = "WAITING",
     var orderPrice: Long = 0,
     var ownerName: String = "",
     var selfwichs: MutableList<Selfwich> = mutableListOf(),
     var products: MutableList<Product> = mutableListOf()
)  {
     fun calculatePrice(){
          orderPrice=0
          selfwichs.forEach { selfwich ->
               orderPrice += selfwich.selfwichPrice
          }
          products.forEach { product ->
               orderPrice += product.pPrice
          }
     }

      fun dateConverter(): String {
          val sdf = SimpleDateFormat("dd/M/yy ")
          val currentDate = sdf.format(date)

          return currentDate
     }
     fun dateConverterToHour(): String {
          val sdf = SimpleDateFormat("hh:mm")
          val currentDate = sdf.format(date)

          return currentDate
     }
     fun priceToString(): String {
          return orderPrice.toString()
     }
}