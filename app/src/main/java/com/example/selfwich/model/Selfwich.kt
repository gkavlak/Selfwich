package com.example.selfwich.model

data class Selfwich(
    var selfwichId:Long= 0 ,
    var selfwichName: String = "",
    var selfwichDesc:String="",
    var selfwichLike: Long = 0,
    var selfwichPrice: Long = 0,
    var selfwichIngredients: MutableList<Ingredient> = mutableListOf()
){
    fun getSelfwichIngredients(): String {
        var strIngr=""
        selfwichIngredients.forEach { ingr->
            strIngr.plus(ingr.ingredientName).plus(",")
        }
        return strIngr
    }
    fun calculateTotalSelfwichPrice() {
        selfwichPrice=0

        this.selfwichIngredients.forEach { ingr->
            this.selfwichPrice= this.selfwichPrice + ingr.ingredientPrice
        }

    }

    fun deleteIngredient(ingredientId:Long){
            selfwichIngredients.forEach { ingredient->
                if (ingredient.ingredientId==ingredientId){
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

}
