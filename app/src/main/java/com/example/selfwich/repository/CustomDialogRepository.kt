package com.example.selfwich.repository

import android.util.Log
import com.example.selfwich.model.Ingredient
import com.example.selfwich.model.Product
import com.google.firebase.firestore.FirebaseFirestore


class CustomDialogRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun addProductToDatabase(product:Product) {
        product.let {
            product.pType="eats"
            val docref= firestore.collection("eats")
                .document(product.pName)
            docref.set(it).addOnSuccessListener { document->
                if (document!= null){
                    Log.i("Click","Databaseye gonderildi $product")
                } else {
                    Log.i("Click","Document bulamdi" + " $product")
                }

            }
                .addOnFailureListener { exception->
                    Log.i("Click", "Olmadi yaaa", exception)
                }
        }
    }
    fun addDrinksToDatabase(product:Product) {
        product.let {
            product.pType="drinks"
            val docref= firestore.collection("drinks")
                .document(product.pName)
            docref.set(it).addOnSuccessListener { document->
                if (document!= null){
                    Log.i("Click","Databaseye gonderildi $product")

                } else {
                    Log.i("Click","Document bulamdi" + " $product")
                }

            }
                .addOnFailureListener { exception->
                    Log.i("Click", "Olmadi yaaa", exception)
                }
        }
    }
    fun addIngredientToDatabase(ingredient: Ingredient){
        ingredient.let {
            ingredient.type="ingredient"
            val docref = firestore.collection("ingredient")
                .document(ingredient.ingredientName)
            docref.set(it).addOnSuccessListener { document ->
                if (document!= null){
                    Log.i("Click","Databaseye gonderildi $ingredient")

                } else {
                    Log.i("Click","Document bulamdi" + " $ingredient")
                }
            }
                .addOnFailureListener { exception->
                Log.i("Click", "Olmadi yaaa", exception)
            }
        }

    }

}