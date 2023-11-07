package com.example.challenge2_binar.database.categoryDB

import androidx.room.TypeConverter
import com.example.challenge2_binar.api.modelCategory.KategoriMenu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConverterCategoryMenu {
    private var gson = Gson()

    @TypeConverter
    fun categoryToString(categoryMenu: KategoriMenu): String {
        return gson.toJson(categoryMenu)
    }

    @TypeConverter
    fun stringToCategory(data: String): KategoriMenu {
        val listType = object : TypeToken<KategoriMenu>() {}.type
        return gson.fromJson(data, listType)
    }
}