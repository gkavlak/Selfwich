package com.example.selfwich.model

import java.util.*

data class Product(
    var pId: String = UUID.randomUUID().toString(),
    var pName: String = "",
    var pDesc:String="",
    var pLike: Long = 0,
    var pPrice: Long = 0,
    var pImage: String = "",
    var pNone:String = ""
){

}
