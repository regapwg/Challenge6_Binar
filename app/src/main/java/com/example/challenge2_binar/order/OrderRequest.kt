package com.example.challenge2_binar.order

import androidx.lifecycle.LiveData

data class
OrderRequest(
    val orders: List<Order?>?,
    val total: LiveData<Int>,
    val username: String?

)