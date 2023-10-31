package com.example.challenge2_binar.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge2_binar.viewModel.DetailViewModel
import com.example.challenge2_binar.viewModel.KeranjangViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(application) as T
        } else if (modelClass.isAssignableFrom(KeranjangViewModel::class.java)) {
            return KeranjangViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}