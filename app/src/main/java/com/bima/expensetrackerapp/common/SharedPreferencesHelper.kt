package com.bima.expensetrackerapp.common

import android.content.Context

class SharedPreferencesHelper(private val context: Context) {
    companion object {
        private const val MY_PREF_KEY = "PREF_KEY"
    }

    fun saveStringData(key:String,value:String?) {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key,value).apply()
    }

    fun getStringData(key: String):String? {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key,null)
    }

    fun clearPreferences() {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}