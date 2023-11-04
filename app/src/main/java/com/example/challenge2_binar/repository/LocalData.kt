package com.example.challenge2_binar.repository

import com.example.challenge2_binar.database.menuDb.Menu
import com.example.challenge2_binar.database.menuDb.MenuDao
import kotlinx.coroutines.flow.Flow

class LocalData (private val menuDao: MenuDao){
    fun readMenu(): Flow<List<Menu>> {
        return menuDao.readMenu()
    }

    suspend fun insertMenu(menu: Menu) {
        menuDao.insertMenu(menu)
    }
}