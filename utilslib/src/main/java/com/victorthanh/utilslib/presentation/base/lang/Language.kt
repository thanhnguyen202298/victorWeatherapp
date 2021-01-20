package com.victorthanh.base.lang

import android.content.Context
import com.victorthanh.utilslib.R

class Language() : Comparable<Language> {

    var code = "en"

    var countryCode = "us"

    var isSelected = false

    constructor(language: String, countryCode: String) : this() {
        this.code = language
        this.countryCode = countryCode
    }

    override fun compareTo(other: Language): Int {
        if (this.code != other.code) return -1
        if (this.isSelected != other.isSelected) return -1
        return 0
    }

    fun getName(context: Context): String = when (code) {
        VIETNAMESE_CODE_LANGUAGE -> context.getString(R.string.general_title_language_vietnamese)
        ENGLISH_CODE_LANGUAGE -> context.getString(R.string.general_title_language_english)
        else -> context.getString(R.string.general_title_language_english)
    }

    companion object {
        val ENGLISH_CODE_LANGUAGE = "en"
        val ENGLISH_CODE_COUNTRY = "us"

        val VIETNAMESE_CODE_LANGUAGE = "vi"
        val VIETNAMESE_CODE_COUNTRY = "vn"

        @JvmStatic
        fun generateAppLanguage(selectedCode: String): ArrayList<Language> {
            val languages = ArrayList<Language>()
            languages.add(Language(ENGLISH_CODE_LANGUAGE, ENGLISH_CODE_COUNTRY))
            languages.add(Language(VIETNAMESE_CODE_LANGUAGE, VIETNAMESE_CODE_COUNTRY))
            languages.map { if (it.code == selectedCode.toLowerCase()) it.isSelected = true }
            return languages
        }
    }
}