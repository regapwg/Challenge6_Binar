package com.example.challenge2_binar.database.menuDb

import androidx.room.TypeConverter
import com.example.challenge2_binar.api.produk.ListMenu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    private var gson = Gson()

    @TypeConverter
    fun menuToString(listMenuResponse: ListMenu): String {
        return gson.toJson(listMenuResponse)
    }

    @TypeConverter
    fun stringToMenu(data: String): ListMenu {
        val listType = object : TypeToken<ListMenu>() {}.type
        return gson.fromJson(data, listType)
    }
}