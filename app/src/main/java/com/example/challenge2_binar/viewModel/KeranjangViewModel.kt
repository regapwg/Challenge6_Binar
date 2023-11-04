package com.example.challenge2_binar.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2_binar.repository.CartRepository
import com.example.challenge2_binar.database.cartDb.SimpleChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeranjangViewModel (private val cartRepository: CartRepository) : ViewModel() {

    val getAllitems: LiveData<List<SimpleChart>> = cartRepository.getAllItems()

    private val _itemLiveData = MutableLiveData<SimpleChart>()
    val itemLiveData: LiveData<SimpleChart> = _itemLiveData

    var totalPrice: LiveData<Int> = cartRepository.totalPrice()

    fun delete(chartId: Long) {
        viewModelScope.launch(Dispatchers.IO)
        {
            cartRepository.deleteById(chartId)
        }
    }

    fun deleteAllItem() {
        viewModelScope.launch(Dispatchers.IO)
        {
            cartRepository.deleteAll()
        }
    }

    fun update(simpleChart: SimpleChart) {
        viewModelScope.launch(Dispatchers.IO)
        {
            cartRepository.update(simpleChart)
        }
        _itemLiveData.value = simpleChart

    }
}