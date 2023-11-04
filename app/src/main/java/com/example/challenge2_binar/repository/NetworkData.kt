package com.example.challenge2_binar.repository

import com.example.challenge2_binar.api.APIService

class NetworkData(private val apiService: APIService) {
    suspend fun getList() = apiService.getList()
    suspend fun getCategory() = apiService.getCategory()
}