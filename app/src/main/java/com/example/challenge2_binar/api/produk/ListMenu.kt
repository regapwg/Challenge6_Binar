package com.example.challenge2_binar.api.produk

data class ListMenu(
    val code: Int?,
    val `data`: List<ListData?>?,
    val message: String?,
    val status: Boolean?
)