package com.victorthanh.weather

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.victorthanh.utilslib.data.preference.PreferenceKey
import com.victorthanh.utilslib.presentation.base.lang.LocaleManager
import us.fuvi.utils_sdk.data.DataHelper

class App: Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(
            LocaleManager.setLocale(
                base!!,
                PreferenceKey.KEY_LANGUAGE.toString(),
                PreferenceKey.KEY_COUNTRY_CODE.toString()
            )
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.setLocale(
            this,
            PreferenceKey.KEY_LANGUAGE.toString(),
            PreferenceKey.KEY_COUNTRY_CODE.toString()
        )
    }

    override fun onCreate() {
        super.onCreate()
        DataHelper(this)
        DataHelper.realmManager?.configRealmDatabase()
    }
}