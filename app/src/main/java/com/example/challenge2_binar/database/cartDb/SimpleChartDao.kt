package com.example.challenge2_binar.database.cartDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface SimpleChartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(simpleChart: SimpleChart)

    @Query("SELECT * FROM simple_chart_table ORDER BY itemId DESC")
    fun getAllCartItems(): LiveData<List<SimpleChart>>

    @Query("DELETE FROM simple_chart_table WHERE itemId = :chartId")
    fun deleteById(chartId: Long)

    @Query("DELETE FROM simple_chart_table")
    fun deleteAll()

    @Update
    fun update(simpleChart: SimpleChart)
}