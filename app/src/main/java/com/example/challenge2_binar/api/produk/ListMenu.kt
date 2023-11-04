package com.example.challenge2_binar.api.produk

import com.google.gson.annotations.SerializedName

data class ListMenu(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<ListData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean?
)