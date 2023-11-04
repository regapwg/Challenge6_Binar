package com.example.challenge2_binar.database.menuDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.challenge2_binar.api.produk.ListMenu

@Entity(tableName = "menu_table")
class Menu (var listMenu: ListMenu){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}