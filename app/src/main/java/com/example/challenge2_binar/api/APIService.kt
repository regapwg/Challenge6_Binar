package com.example.challenge2_binar.api

import com.example.challenge2_binar.modelCategory.KategoriMenu
import com.example.challenge2_binar.order.OrderRequest
import com.example.challenge2_binar.order.OrderResponse
import com.example.challenge2_binar.produk.ListMenu
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("category")
    fun getCategory(): Call<KategoriMenu>

    @GET("listmenu")
    suspend fun getList(): ListMenu

    @POST("order")
    fun postOrder(@Body orderRequest: OrderRequest): Call<OrderResponse>

}