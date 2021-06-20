package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Product
import com.example.selfwich.model.Selfwich
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class SelfWichRepository {
    private val _sandwichList = MutableLiveData<ArrayList<Selfwich>>()
    val sandwichList: LiveData<ArrayList<Selfwich>> = _sandwichList

    private val _isSelfWichLikeAdded=MutableLiveData<Long>()
    val isSelfWichLikeAdded: LiveData<Long> = _isSelfWichLikeAdded


    private  var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        getAllSandwich()
    }
    fun getAllSandwich() {
        firestore.collection("selfSandwich").addSnapshotListener { docSnapshot, e ->
            if(e != null){
                Log.w(ContentValues.TAG , e.message.toString())
                return@addSnapshotListener
            }
            if (docSnapshot != null){
                val allSandwich= ArrayList<Selfwich>()
                docSnapshot.documents.forEach{
                    val currentSandwich = it.toObject(Selfwich::class.java)
                    it.let {
                        allSandwich.add(currentSandwich!!)
                        _sandwichList.value =allSandwich
                    }
                }
            }
        }
    }

    fun addLikeToSelfWich(selfwich: Selfwich){
        val plus: Long = 1
        firestore.collection("selfSandwich")
            .document(selfwich.selfwichName)
            .update("selfwichLike", FieldValue.increment(plus))
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
                _isSelfWichLikeAdded.value = selfwich.selfwichLike

            }
            .addOnFailureListener{ e->Log.d(ContentValues.TAG, "DocumentSnapshot ${e.message}!") }
    }
    fun deleteSelfwichToDatabase(selfwich: Selfwich){
        firestore.collection("selfSandwich").document(selfwich.selfwichName)
            .delete()
            .addOnSuccessListener {

                Log.i("delete", "DocumentSnapshot successfully deleted!"+"${it}")  }
            .addOnFailureListener { e ->

                Log.w(ContentValues.TAG, "Error deleting document", e) }

    }

}