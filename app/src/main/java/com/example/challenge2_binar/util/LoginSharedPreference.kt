package com.example.challenge2_binar.util

import android.content.Context


class LoginSharedPreference(context: Context) {
    private val sharedPreference = "userPrefer"
    private var preferences = context.getSharedPreferences(sharedPreference, 0)
    private val editor = preferences.edit()

    fun setPreferences(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getPreferences(key: String): String? {
        return preferences.getString(key, null)
    }

    fun clearPreferences(){
       editor.clear()
       editor.commit()
    }

    companion object{
        const val is_login = "is_login"
    }
}