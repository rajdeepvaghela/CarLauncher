package com.rdapps.carlauncher.data

import android.content.Context

const val USER_PREFERENCE_NAME = "shortcut_data"

fun Context.save(key: String, value: String) {
    val pref = getSharedPreferences(USER_PREFERENCE_NAME, Context.MODE_PRIVATE)
    pref.edit().putString(key, value).apply()
}

fun Context.read(key: String): String {
    val pref = getSharedPreferences(USER_PREFERENCE_NAME, Context.MODE_PRIVATE)
    return pref.getString(key, "") ?: ""
}