package com.example.challenge2_binar.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

import com.example.challenge2_binar.repository.MenuRepository
import com.example.challenge2_binar.util.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel (private val repository: MenuRepository) : ViewModel() {

    val isGrid = MutableLiveData<Boolean>().apply {
        value = true
    }
    fun getAllCategory() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success( repository.getCategory()))
        } catch (exception: Exception) {
            emit(Resource.error(null,exception.message ?: "Error Occurred!"))
        }
    }

    fun getAllList() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success( repository.getList()))
        } catch (exception: Exception) {
            emit(Resource.error(null,exception.message ?: "Error Occurred!"))
        }
    }
}