package com.example.challenge2_binar.repository

import com.example.challenge2_binar.api.APIService

class MenuRepository(private val apiService: APIService) {
    suspend fun getList() = apiService.getList()
}