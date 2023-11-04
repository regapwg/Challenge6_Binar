package com.example.challenge2_binar.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.challenge2_binar.database.cartDb.SimpleChart
import com.example.challenge2_binar.database.cartDb.SimpleChartDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartRepository(private val simpleChartDao: SimpleChartDao)  {


    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun insert(simpleChart: SimpleChart) {
        executorService.execute {
            simpleChartDao.insert(simpleChart)
        }
    }

    fun update(simpleChart: SimpleChart) {
        executorService.execute {
            simpleChartDao.update(simpleChart)
        }
    }

    fun deleteById(chartId: Long) {
        executorService.execute {
            simpleChartDao.deleteById(chartId)
        }
    }

    fun deleteAll(){
        executorService.execute {
            simpleChartDao.deleteAll()

        }
    }

    fun getAllItems(): LiveData<List<SimpleChart>> = simpleChartDao.getAllCartItems()

    fun totalPrice() : LiveData<Int> {
        return simpleChartDao.getAllCartItems().map { cartItems ->
            var total = 0
            for (cartItem in cartItems) {
                total += cartItem.itemPrice * cartItem.itemQuantity
            }
            total
        }
    }
}