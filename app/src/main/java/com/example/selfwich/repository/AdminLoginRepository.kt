package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.DomainUser
import com.example.selfwich.model.Product
import com.example.selfwich.model.Singleton
import com.google.firebase.firestore.FirebaseFirestore

class AdminLoginRepository (){

    private  var firestore: FirebaseFirestore

    private val _loginSucces : MutableLiveData<Boolean> = MutableLiveData(null)
      val loginSucces : LiveData<Boolean> = _loginSucces


    init {
        firestore = FirebaseFirestore.getInstance()
    }

    fun adminLogin(email: String ,password:String ){

        firestore.collection("adminUser").addSnapshotListener { docSnapshot, e ->
            if( e != null){
                Log.w(ContentValues.TAG, e.message.toString())
                return@addSnapshotListener
            }
            if(docSnapshot != null){
                docSnapshot.documents.forEach {
                    val currentAdmin = it.toObject(DomainUser::class.java)

                    if(currentAdmin?.userMail == email && currentAdmin?.userPw == password ){
                        Singleton.globalUser.value = currentAdmin
                        _loginSucces.value = true
                        return@addSnapshotListener
                    }
                    else{
                        _loginSucces.value= false

                    }
                }
            }
        }
    }

}