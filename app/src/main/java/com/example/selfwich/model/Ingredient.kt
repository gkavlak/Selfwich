package com.example.selfwich.model

data class Ingredient(
    var ingredientName:String="",
    var ingredientId : Long =0,
    var ingredientPrice: Long =0,
    var ingredientDesc: String="",
    var ingredientImage: String=""
){}