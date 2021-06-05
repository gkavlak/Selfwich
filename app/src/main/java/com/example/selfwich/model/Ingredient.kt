package com.example.selfwich.model

data class Ingredient(
    var ingredientId : Long =0,
    var ingredientName:String="",
    var ingredientIsAdded: Boolean= false,
    var ingredientPrice: Long =0,
    var ingredientDesc: String="",
    var ingredientImage: String=""


){
    fun isSelected(){
        this.ingredientIsAdded= !ingredientIsAdded
    }

}