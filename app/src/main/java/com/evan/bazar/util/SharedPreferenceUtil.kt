package com.evan.bazar.util

import android.content.Context

object SharedPreferenceUtil {
    private const val mSharedName = "workers_app"
    const val TYPE_AUTH_TOKEN = "auth_token"
    const val TYPE_AUTH_REFRESH_TOKEN = "refresh_token"
    const val TYPE_PUSH_TOKEN = "firebase_push_token"

    fun saveShared(c: Context, type: String, value: String) {
        val ed = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit()
        ed.putString(type, value)
        ed.commit()
    }
    fun saveShared(c: Context, type: String, value: Int) {
        val ed = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit()
        ed.putInt(type, value)
        ed.commit()
    }

    fun removeShared(c: Context, type: String) {
        val ed = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit()
        ed.remove(type)
        ed.commit()
    }

    fun getShared(c: Context, type: String): String? {
        return c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).getString(type, "")
    }
    fun getSharedInt(c: Context, type: String): Int? {
        return c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).getInt(type, 0)
    }
}