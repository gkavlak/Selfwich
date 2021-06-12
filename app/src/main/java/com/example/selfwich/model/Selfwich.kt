package com.example.selfwich.model

import java.util.*

data class Selfwich(
    var selfwichId: String= UUID.randomUUID().toString(),
    var selfwichName: String = "",
    var selfwichDesc:String="",
    var selfwichLike: Long = 0,
    var userName:String="",
    var userId: String="",
    var selfwichPrice: Long = 0,
    var selfwichIngredients: MutableList<Ingredient> = mutableListOf()

){
    fun calculateTotalSelfwichPrice() {
        selfwichPrice=0

        this.selfwichIngredients.forEach { ingr->
            this.selfwichPrice= this.selfwichPrice + ingr.ingredientPrice
        }

    }
    fun deleteIngredient(ingredientId:Long){
            selfwichIngredients.forEach { ingredient->
                if (ingredient.ingredientId.equals(ingredientId)){
                    selfwichIngredients.remove(ingredient)
                }
            }
    }
    fun selfwichIngredientToStrings(): String {
        var ingredientStrng=""

        selfwichIngredients.forEach {ingrd->
            ingredientStrng= ingredientStrng + ingrd.ingredientName.toString() + ","
        }
        return ingredientStrng
    }
    fun reNameSelfwich(name:String){
        selfwichName=name
    }
    fun reDescSelfwich(name:String){
        selfwichDesc= name
    }

}
