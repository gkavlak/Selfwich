package com.example.selfwich.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfwich.model.Singleton
import com.example.selfwich.model.UserSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsRepository {
    private val _currentAuthUser = MutableLiveData<UserSettings>()
    val currentAuthUser: LiveData<UserSettings> = _currentAuthUser
    private val _isSignedOut = MutableLiveData<Boolean?>()
    val isSignedOut: LiveData<Boolean?> = _isSignedOut

    private val _updateStatus = MutableLiveData<AuthStatus>()
    val updateStatus: LiveData<AuthStatus> = _updateStatus

    private val _isUserUpdated = MutableLiveData<Boolean>()
    val isUserUpdated: LiveData<Boolean> = _isUserUpdated


    init {
        settingUserInit()
    }

    fun updateUserInformation(newUser: UserSettings){
        val user = Firebase.auth.currentUser
        if(user != null){
            //user signed in
            _updateStatus.value=AuthStatus.LOADING

            val profileChangeRequest= UserProfileChangeRequest.Builder()
                .setDisplayName(newUser.userDisplayName).build()

            user.updateProfile(profileChangeRequest)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        user.updateEmail(newUser.userEmail)
                            .addOnSuccessListener {
                                user.updatePassword(newUser.userPassword)
                                    .addOnSuccessListener {
                                        _isUserUpdated.value=true
                                        _updateStatus.postValue((AuthStatus.DONE))
                                        Singleton.globalUser.value?.userName = newUser.userDisplayName
                                    }
                            }
                    }else{
                            Log.i("Settings Repo",it.exception.toString())
                        _updateStatus.value=AuthStatus.ERROR
                    }
                }
        } else{
            _currentAuthUser.value = UserSettings("Ooops", "Ooops")
        }
    }

    fun settingUserInit(){

        val userName = Singleton.globalUser.value?.userName

        val user= Firebase.auth.currentUser

        if (user != null){
            _currentAuthUser.value=
                UserSettings(userDisplayName = userName!! ,userEmail = user.email)
            Log.i("userNameee","${_currentAuthUser.value?.userDisplayName}")
              //user is signed in
        }
        else{
            _currentAuthUser.value= UserSettings("something wrong","oops" )
        }
    }

    fun signOut(){
        FirebaseAuth.getInstance().signOut()
        _isSignedOut.value=true
    }
    fun signedOutCompleted(){
        _isSignedOut.value=null
    }
}