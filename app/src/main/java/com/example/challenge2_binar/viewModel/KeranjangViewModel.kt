package com.example.challenge2_binar.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2_binar.repository.Repository
import com.example.challenge2_binar.database.SimpleChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeranjangViewModel (private val repository: Repository) : ViewModel() {

    val getAllitems: LiveData<List<SimpleChart>> = repository.getAllItems()

    private val _itemLiveData = MutableLiveData<SimpleChart>()
    val itemLiveData: LiveData<SimpleChart> = _itemLiveData

    var totalPrice: LiveData<Int> = repository.totalPrice()

    fun delete(chartId: Long) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteById(chartId)
        }
    }

    fun deleteAllItem() {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteAll()
        }
    }

    fun update(simpleChart: SimpleChart) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.update(simpleChart)
        }
        _itemLiveData.value = simpleChart

    }
}