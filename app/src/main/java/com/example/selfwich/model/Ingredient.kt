package com.example.selfwich.model

data class Ingredient(
    var ingredientId : Long = 0,
    var ingredientName:String="",
    var ingredientIsAdded: Boolean= false,
    var ingredientPrice: Long =0,
    var ingredientDesc: String="",
    var ingredientImage: String="",
    var type:String="ingredient"


){
    fun isSelected(){
        this.ingredientIsAdded= !ingredientIsAdded
    }

}