package com.example.geekhavencommunityapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserModel: ViewModel()
{
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _username = MutableLiveData<String>()


    init{
        _email.value = "empty"
        _password.value = "empty"
        _username.value = "empty"
    }

    val username: String
        get() = _username.value ?: "null"
    val email: String
        get() = _email.value ?: "null"
    val password: String
        get() = _password.value ?: "null"

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    override fun onCleared() {
        super.onCleared()

    }
}