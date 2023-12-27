package com.example.geekhavencommunityapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

object UserModel
{
    private val _email = MutableLiveData<String?>()
    private val _password = MutableLiveData<String?>()
    private val _username = MutableLiveData<String?>()

    init{
        _email.value = ""
        _username.value= ""
        _password.value = ""
    }
    val username: String?
        get() = _username.value
    val email: String?
        get() = _email.value
    val password: String?
        get() = _password.value

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

}