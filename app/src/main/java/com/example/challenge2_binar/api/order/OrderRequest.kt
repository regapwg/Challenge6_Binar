package com.example.challenge2_binar.api.order

import androidx.lifecycle.LiveData

data class
OrderRequest(
    val orders: List<Order?>?,
    val total: LiveData<Int>,
    val username: String?

)