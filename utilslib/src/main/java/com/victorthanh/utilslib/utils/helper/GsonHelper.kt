package com.victorthanh.utilslib.utils.helper

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonHelper {

    private var gson: Gson? = null

    @JvmStatic
    val gsonInstance: Gson
        @Synchronized get() {
            if (gson == null) {
                val gsonTemp = GsonBuilder()
                gson = gsonTemp.create()
            }
            return gson!!
        }

}