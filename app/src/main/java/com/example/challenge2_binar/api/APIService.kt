package com.example.challenge2_binar.api

import com.example.challenge2_binar.api.modelCategory.KategoriMenu
import com.example.challenge2_binar.api.order.OrderRequest
import com.example.challenge2_binar.api.order.OrderResponse
import com.example.challenge2_binar.api.produk.ListMenu
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("category")
    suspend fun getCategory(): KategoriMenu

    @GET("listmenu")
    suspend fun getList(): ListMenu

    @POST("order")
    fun postOrder(@Body orderRequest: OrderRequest): Call<OrderResponse>

}