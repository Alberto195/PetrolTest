package com.example.petroltest.repository

import androidx.lifecycle.MutableLiveData

object Repository {
    var notification = MutableLiveData<Int>()
    var error = MutableLiveData<String>()
}