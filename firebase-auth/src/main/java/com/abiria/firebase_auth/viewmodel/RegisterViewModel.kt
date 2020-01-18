package com.abiria.firebase_auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    val phone = MutableLiveData<String>()
    val code = MutableLiveData<String>()

}