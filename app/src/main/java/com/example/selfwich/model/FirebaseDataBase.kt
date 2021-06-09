package com.example.selfwich.model

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.repository.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseDataBase() {
    private var db: FirebaseFirestore= Firebase.firestore

    fun addNewUserToFireStore(userId: String, name: String):Boolean{
        var isUserImplemented: Boolean = false
        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener() {
                    if(it.result?.data == null){
                        val data:DomainUser= DomainUser(userId = userId, userName = name)
//                        val data= hashMapOf(
//                                "userId" to userId,
//                                "userName" to name)

                        db.collection("users").document(userId)
                                .set(data)
                                .addOnSuccessListener{
                                    isUserImplemented=true
                                    Log.i("FirebaseDatabase", "User Successfully implemented")
                                }
                                .addOnFailureListener {
                                    Log.i("FirebaseDatabase", "User UnSuccessfully implemented ${it}")
                                }
                    }
                }
        return isUserImplemented
    }
    fun userIdToName(userId: String){
       val docRef = db.collection("users").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val currentUser = document.toObject(DomainUser::class.java)

                    Log.i("userNameee", "DocumentSnapshot data: ${document.data}")
                    Log.i("userNameee", "DocumentSnapshot data: ${currentUser}")
                } else {
                    Log.i("userNameee", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.i("userNameee", "get failed with ", exception)
            }
    }

}