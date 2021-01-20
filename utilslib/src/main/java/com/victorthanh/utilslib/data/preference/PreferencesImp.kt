package com.victorthanh.utilslib.data.preference

interface PreferencesImp {
    fun clear(key: Any)
    fun contains(key: Any):Boolean

    operator fun <T> get(key: Any, anonymousClass: Class<T>): T

    fun <T> put(key: Any, data: T)
}