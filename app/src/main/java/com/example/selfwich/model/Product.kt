package com.example.selfwich.model

data class Product(
    var pId: Long = 0,
    var pName: String = "",
    var pDesc:String="",
    var pLike: Long = 0,
    var pPrice: Long = 0,
    var pImage: String = ""
){

}