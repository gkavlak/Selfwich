package com.example.selfwich.repository

import com.google.firebase.firestore.FirebaseFirestore

class CurrentOrderRepository {
    private  var firestore: FirebaseFirestore



    init {
        firestore = FirebaseFirestore.getInstance()
    }
}