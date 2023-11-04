package com.example.challenge2_binar.database.cartDb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "simple_chart_table" )
data class SimpleChart(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0,

    @ColumnInfo(name = "item_name")
    var itemName: String,

    @ColumnInfo(name = "item_quantity")
    var itemQuantity: Int,

    @ColumnInfo(name = "item_image")
    var itemImage: String?,

    @ColumnInfo(name = "item_price")
    var itemPrice: Int,

    @ColumnInfo(name = "total_price")
    var totalPrice: Int,

    ): Parcelable