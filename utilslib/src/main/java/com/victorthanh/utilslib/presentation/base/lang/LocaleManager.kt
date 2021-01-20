package com.victorthanh.utilslib.presentation.base.lang

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.preference.PreferenceManager
import com.victorthanh.base.lang.Language
import java.util.*


object LocaleManager {

    @JvmStatic
    fun setLocale(context: Context, preKeyLanguage: String, preKeyCountryCode: String): Context {

        /* This methods be called on application have not been created */
        val sharePreference = PreferenceManager.getDefaultSharedPreferences(context)
        var language = sharePreference.getString(preKeyLanguage, "")
        var countryCode = sharePreference.getString(preKeyCountryCode, "")

        /* Check for the first time launch app, save default language */
        if (language == "" || countryCode == "" || language == null || countryCode == null) {
            language = Locale.getDefault().language ?: Language.ENGLISH_CODE_LANGUAGE
            countryCode = Locale.getDefault().country ?: Language.ENGLISH_CODE_COUNTRY
            saveLanguageInPreference(context, language, countryCode, preKeyLanguage, preKeyCountryCode)
            return context
        }

        return updateResources(context, language, countryCode)
    }

    @JvmStatic
    fun setNewLocale(context: Context, language: String, countryCode: String, preKeyLanguage: String, preKeyCountryCode: String): Context {
        saveLanguageInPreference(context, language, countryCode, preKeyLanguage, preKeyCountryCode)
        return updateResources(context, language, countryCode)
    }

    @JvmStatic
    @SuppressLint("ApplySharedPref")
    private fun saveLanguageInPreference(context: Context, language: String, countryCode: String, preKeyLanguage: String, preKeyCountryCode: String) {
        val sharePreference = PreferenceManager.getDefaultSharedPreferences(context)
        sharePreference.edit().putString(preKeyLanguage, language).commit()
        sharePreference.edit().putString(preKeyCountryCode, countryCode).commit()
    }

    @JvmStatic
    @SuppressLint("ObsoleteSdkInt")
    private fun updateResources(context: Context, language: String, countryCode: String): Context {
        var contextTemp = context
        val locale = Locale(language, countryCode)
        Locale.setDefault(locale)

        val resource = contextTemp.resources
        val config = resource.configuration

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                config.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                contextTemp = contextTemp.createConfigurationContext(config)
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                config.setLocale(locale)
                contextTemp = contextTemp.createConfigurationContext(config)
            }

            else -> {
                @Suppress("DEPRECATION")
                config.locale = locale
                @Suppress("DEPRECATION")
                resource.updateConfiguration(config, resource.displayMetrics)
            }
        }
        return contextTemp
    }

    @JvmStatic
    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        @Suppress("DEPRECATION")
        return if (Build.VERSION.SDK_INT >= 24) config.locales.get(0) else config.locale
    }

}