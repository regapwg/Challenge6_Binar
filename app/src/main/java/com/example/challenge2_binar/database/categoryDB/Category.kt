package com.example.challenge2_binar.database.categoryDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.challenge2_binar.api.modelCategory.KategoriMenu


@Entity(tableName = "category_table")
class Category (var categoryMenu: KategoriMenu){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}