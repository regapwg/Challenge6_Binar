package com.example.challenge2_binar.api.modelCategory

import com.google.gson.annotations.SerializedName

data class KategoriMenu(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<KategoriData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean?
)