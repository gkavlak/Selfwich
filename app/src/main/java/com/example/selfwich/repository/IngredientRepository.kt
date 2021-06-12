package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.DomainUser
import com.example.selfwich.model.Ingredient
import com.example.selfwich.model.Selfwich
import com.example.selfwich.model.UserSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

class IngredientRepository{
    private val _ingredientList=MutableLiveData<ArrayList<Ingredient>>()
    val ingredientList: LiveData<ArrayList<Ingredient>> =_ingredientList


    private  var firestore: FirebaseFirestore
    init {

        firestore = FirebaseFirestore.getInstance()
        getAllIngredient()
    }

    fun getAllIngredient(){
        firestore.collection("ingredient").addSnapshotListener { docSnapshot, e ->
            if(e != null){
                Log.w(ContentValues.TAG , e.message.toString())
                return@addSnapshotListener
            }
            if (docSnapshot != null){
                val allIngredient=ArrayList<Ingredient>()
                docSnapshot.documents.forEach{
                    val currentIngredient = it.toObject(Ingredient::class.java)
                    it.let {
                        allIngredient.add(currentIngredient!!)
                        _ingredientList.value =allIngredient
                    }
                }
            }
        }

    }
    fun getUserIdfromDatabase(): String {
        var name= ""
        val user= Firebase.auth.currentUser

        if (user != null){
                name = user.displayName!!
            Log.i("userNameee","${name}")
            return name

        }
        else{
           name= "isim bulunamadi"
            Log.i("userNameee","${name}")
            return name
        }

//        val docRef = firestore.collection("users").document("m1GAyofxJEXolVtbVoz8166256D2")
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    val user = FirebaseAuth.getInstance().currentUser
//                    val currentUser = document.toObject(DomainUser::class.java)
//                    Log.i("userNameee", "DocumentSnapshot data: ${currentUser}")
//                } else {
//                    Log.i("userNameee", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.i("userNameee", "get failed with ", exception)
//            }


    }
    fun writeNewSelfwichToDatabase(selfwich: Selfwich){
        selfwich.let {
            val docref=firestore.collection("selfSandwich").document(selfwich.selfwichName)
            docref.set(it).addOnSuccessListener { document->
                if (document!= null){
                    Log.i("Click","Databaseye gonderildi $selfwich")
                } else {
                    Log.i("Click","Document bulamdi" + " $selfwich")
                }
            }
                .addOnFailureListener { exception->
                    Log.i("Click", "Olmadi yaaa", exception)
                }

        }



    }

}