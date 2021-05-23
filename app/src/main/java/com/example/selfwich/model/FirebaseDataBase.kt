package com.example.selfwich.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseDataBase() {
    private var db: FirebaseFirestore= Firebase.firestore

    fun addNewUserToFireStore(userId: String):Boolean{
        var isUserİmplemented: Boolean = false
        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener() {
                    if(it.result?.data == null){
                        val data= hashMapOf(
                                "userId" to userId
                        )
                        db.collection("users").document(userId)
                                .set(data)
                                .addOnSuccessListener{
                                    isUserİmplemented=true
                                    Log.i("FirebaseDatabase", "User Successfully implemented")
                                }
                                .addOnFailureListener {
                                    Log.i("FirebaseDatabase", "User UnSuccessfully implemented ${it}")
                                }
                    }
                }
        return isUserİmplemented
    }

}