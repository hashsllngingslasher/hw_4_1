package com.example.hw_4_1.prefs

import android.content.Context

class Prefs(
    context: Context
) {
    private val prefs = context.getSharedPreferences(
        "prefs", Context.MODE_PRIVATE
    )

    fun saveImage(url: String) {
        prefs.edit().putString("img", url).apply()
    }

    fun getImage(): String? {
        return prefs.getString("img", "")
    }

    fun saveName(name: String) {
        prefs.edit().putString("name", name).apply()
    }

    fun getName(): String? {
        return prefs.getString("name", "")
    }

    fun makeFirstLaunch() {
        prefs.edit().putBoolean("firstLaunch", false).apply()
    }

    fun isFirstLaunch(): Boolean {
        return prefs.getBoolean("firstLaunch", true)
    }
}

//fun Fragment.getMyPrefs() = Prefs(requireContext())
