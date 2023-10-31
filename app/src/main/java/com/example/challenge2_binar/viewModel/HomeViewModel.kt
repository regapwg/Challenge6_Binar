package com.example.challenge2_binar.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val isGrid = MutableLiveData<Boolean>().apply {
        value = true
    }
}