package com.victorthanh.utilslib.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.victorthanh.utilslib.utils.helper.GsonHelper

class Preferences constructor(context: Context) : PreferencesImp {
    /***
     * Delete a share pref data by a given key
     * @param key
     */
    override fun clear(key: Any) {
        val editor = sharedPreferences.edit()
        editor.remove(key.toString())
        editor.apply()
    }

    override fun contains(key: Any): Boolean {
        return sharedPreferences.contains(key.toString())
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FUVIIVN", Context.MODE_PRIVATE)


    override operator fun <T> get(key: Any, anonymousClass: Class<T>): T {
        val keyTemp: String = when (key) {
            is String -> key
            else -> key.toString()
        }
        return if (anonymousClass == String::class.java) {
            sharedPreferences.getString(keyTemp, "") as T
        } else if (anonymousClass == Boolean::class.java) {
            (sharedPreferences.getBoolean(keyTemp, false)) as T
        } else if (anonymousClass == Float::class.java) {
            java.lang.Float.valueOf(sharedPreferences.getFloat(keyTemp, -1f)) as T
        } else if (anonymousClass == Int::class.java) {
            Integer.valueOf(sharedPreferences.getInt(keyTemp, -1)) as T
        } else if (anonymousClass == Long::class.java) {
            java.lang.Long.valueOf(sharedPreferences.getLong(keyTemp, -1)) as T
        } else {
            GsonHelper.gsonInstance.fromJson(
                sharedPreferences.getString(keyTemp, ""),
                anonymousClass
            )
        }
    }

    override fun <T> put(key: Any, data: T) {
        val keyTemp = when (key) {
            is String -> key
            else -> key.toString()
        }
        val editor = sharedPreferences.edit()
        if (data is String) {
            editor.putString(keyTemp, data as String)
        } else if (data is Boolean) {
            editor.putBoolean(keyTemp, data as Boolean)
        } else if (data is Float) {
            editor.putFloat(keyTemp, data as Float)
        } else if (data is Int) {
            editor.putInt(keyTemp, data as Int)
        } else if (data is Long) {
            editor.putLong(keyTemp, data as Long)
        } else {
            editor.putString(keyTemp, GsonHelper.gsonInstance.toJson(data))
        }
        editor.apply()
    }


}