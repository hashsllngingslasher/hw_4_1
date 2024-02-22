package com.example.hw_4_1.data

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs (
    context: Context
) {
    private val prefs = context.getSharedPreferences(
        "prefs", MODE_PRIVATE
    )

    fun saveImage(url: String) {
        prefs.edit().putString("img", url).apply()
    }

    fun getImage():String? {
        return prefs.getString("img", "")
    }

    fun saveName(url: String) {
        prefs.edit().putString("name", url).apply()
    }

    fun getName():String? {
        return prefs.getString("name", "")
    }
}
