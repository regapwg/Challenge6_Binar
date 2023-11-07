package com.example.challenge2_binar.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.challenge2_binar.api.modelCategory.KategoriMenu
import com.example.challenge2_binar.api.produk.ListMenu
import com.example.challenge2_binar.database.categoryDB.Category
import com.example.challenge2_binar.database.menuDb.Menu
import com.example.challenge2_binar.repository.MenuRepository
import com.example.challenge2_binar.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MenuRepository) : ViewModel() {

    val isGrid = MutableLiveData<Boolean>().apply { value = true }


    val readCategory: LiveData<List<Category>> = repository.local.readCategory().asLiveData()
    private fun insertCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertCategory(category)
    }

    private var _categoryMenu: MutableLiveData<Resources<KategoriMenu>> = MutableLiveData()
    val categoryMenu: LiveData<Resources<KategoriMenu>> get() = _categoryMenu


    val readMenu: LiveData<List<Menu>> = repository.local.readMenu().asLiveData()
    private fun insertMenu(menu: Menu) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertMenu(menu)
    }

    private var _listMenu: MutableLiveData<Resources<ListMenu>> = MutableLiveData()
    val listMenu: LiveData<Resources<ListMenu>> get() = _listMenu


    fun getCategoryMenu() = viewModelScope.launch {
        getAllCategory()
    }

    private suspend fun getAllCategory() {
        try {
            val response = repository.remote.getCategory()
            _categoryMenu.value = Resources.Success(response)

            val categoryMenu = _categoryMenu.value!!.data
            if (categoryMenu != null) {
                offlineCategory(categoryMenu)
            }
        } catch (exception: Exception) {
            _categoryMenu.value = Resources.Error("Error: $exception")
        }
    }
    private fun offlineCategory(categoryMenu: KategoriMenu) {
        val category = Category(categoryMenu)
        insertCategory(category)
    }

    fun getListMenu() = viewModelScope.launch {
        getAllList()
    }

    private suspend fun getAllList() {
        try {
            val responses = repository.remote.getListt()
            _listMenu.value = Resources.Success(responses)

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

}