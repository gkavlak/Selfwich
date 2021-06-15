package com.example.selfwich.repository

import com.google.firebase.firestore.FirebaseFirestore

class OrderDetailsRepository {


    private  var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()


    }
}