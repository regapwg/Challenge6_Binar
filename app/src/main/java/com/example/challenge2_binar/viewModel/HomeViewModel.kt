package com.example.challenge2_binar.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.challenge2_binar.api.produk.ListMenu
import com.example.challenge2_binar.database.menuDb.Menu
import com.example.challenge2_binar.repository.MenuRepository
import com.example.challenge2_binar.util.Resources
import com.example.challenge2_binar.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: MenuRepository) : ViewModel() {

    val isGrid = MutableLiveData<Boolean>().apply { value = true }

    val readMenu: LiveData<List<Menu>> = repository.local.readMenu().asLiveData()
    private fun insertMenu(menu: Menu) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertMenu(menu)
    }

    private var _listMenu: MutableLiveData<Resources<ListMenu>> = MutableLiveData()
    val listMenu: LiveData<Resources<ListMenu>> get() = _listMenu

    fun getListMenu() = viewModelScope.launch {
        getAllList()
    }

    fun getAllCategory() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repository.remote.getCategory()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    private suspend fun getAllList() {
        try {
            val response = repository.remote.getList()
            _listMenu.value = responseListMenu(response)

            val listMenu = _listMenu.value!!.data
            if (listMenu != null) {
                offlineMenu(listMenu)
            }
        } catch (exception: Exception) {
            _listMenu.value = Resources.Error("Error: $exception")
        }
    }

    private fun offlineMenu(listMenu: ListMenu) {
        val menu = Menu(listMenu)
        insertMenu(menu)
    }

    private fun responseListMenu(response: Response<ListMenu>): Resources<ListMenu> {
        return when {
            response.isSuccessful -> {
                val listMenu = response.body()
                Resources.Success(listMenu!!)
            }

            else -> {
                Resources.Error(response.message())
            }
        }
    }
}