package com.example.selfwich.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.DomainUser
import com.example.selfwich.model.FirebaseDataBase
import com.example.selfwich.model.Ingredient
import com.example.selfwich.model.Singleton
import com.example.selfwich.ui.login.LoginResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

enum class AuthStatus { LOADING, DONE, ERROR }

class AuthRepository{
    private val _fireBaseAuthResult = MutableLiveData<LoginResult>()
    val fireBaseAuthResult: LiveData<LoginResult> = _fireBaseAuthResult

    private val _authStatus = MutableLiveData<AuthStatus>()
    val authStatus: LiveData<AuthStatus> = _authStatus

    val db=FirebaseDataBase()

    fun registerUser(email: String,  password:String, name:String) {
        _authStatus.value = AuthStatus.LOADING

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { registerResult ->
                try {
                    if (registerResult.isSuccessful) {
                        db.addNewUserToFireStore(registerResult.result?.user!!.uid,name)
                        val user = FirebaseAuth.getInstance().currentUser
                        Singleton.globalUser.value?.userId=registerResult.result?.user!!.uid
                        Singleton.globalUser.value?.userName=name
                        Singleton.globalUser.value?.userType="customer"
                        Singleton.globalOrderLive.value?.ownerId=registerResult.result?.user!!.uid
                        Singleton.globalOrderLive.value?.ownerName=name
                            if(user != null){
                                initUser(registerResult.result!!)

                            }

                    } else {
                        Log.i("Auth", registerResult.exception?.message.toString())

                        _fireBaseAuthResult.postValue(LoginResult(error = registerResult.exception?.message))
                        _authStatus.postValue(AuthStatus.ERROR)
                    }

                } catch (e: Exception) {

                    throw e
                }
            }
    }

    fun loginUser(email: String, password: String){
            _authStatus.postValue(AuthStatus.LOADING)

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { loginResult->
                    if(loginResult.isSuccessful){
                        initUser(loginResult.result!!)
                        userCameFromdatebase()
                    }
                    else{
                        if (loginResult.exception != null) {

                            Log.i("Auth", loginResult.exception?.message.toString())
                            _authStatus.value = AuthStatus.ERROR
                            _fireBaseAuthResult.postValue(LoginResult(error = loginResult.exception?.message))

                        }

                    }

                }
        }

    private fun initUser(registerResult: AuthResult) {
       _fireBaseAuthResult.postValue(LoginResult(success = registerResult.user))
        _authStatus.postValue(AuthStatus.DONE)
    }

    private fun userCameFromdatebase(){
        val userId= Firebase.auth.currentUser?.uid
        userId?.let { FirebaseFirestore.getInstance().collection("users").document(it).addSnapshotListener { docSnapshot, e ->
            if(e != null){
                Log.w(ContentValues.TAG , e.message.toString())
                return@addSnapshotListener
            }
            if (docSnapshot != null)
            {
                val currentuser = docSnapshot.toObject(DomainUser::class.java)
                Singleton.globalUser.value =currentuser
                Singleton.globalOrderLive.value?.ownerId=currentuser?.userId.toString()
                Singleton.globalOrderLive.value?.ownerName=currentuser?.userName.toString()


            }
        }
        }
    }




}
