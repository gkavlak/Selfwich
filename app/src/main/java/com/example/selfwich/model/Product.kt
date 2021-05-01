package com.example.selfwich.model

data class Product(
    var pId: Long = 0,
    var productName: String = "",
    var productDesc:String="",
    var productLike:Long=0,
    var productPrice: Long = 0,
    var productImage: String = ""
){

}