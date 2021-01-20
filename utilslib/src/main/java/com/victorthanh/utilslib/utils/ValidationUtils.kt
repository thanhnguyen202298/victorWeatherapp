package com.victorthanh.utilslib.utils

import android.content.Context
import android.view.View
import android.widget.TextView

object ValidationUtils {
    fun checkLoginForm(
        context: Context,
        infoLogin: Pair<String, String>,
        errorLogin: Pair<TextView, TextView>
    ): Boolean {

//        val valid = validEmail(infoLogin.first, errorLogin.first, context)
//        val valid2 = validPass(infoLogin.second, errorLogin.second, context)

        return false // valid && valid2
    }

    fun validEmail(inputEmail: String, errorMsg: TextView, context: Context, stringId: Int): Boolean {
        return when {
            inputEmail.isEmpty() -> {
                errorMsg.visibility = View.VISIBLE
                errorMsg.text =
                    context.resources.getString(stringId)
                false
            }

            !isValidEmail(inputEmail) -> {
                errorMsg.visibility = View.VISIBLE
                errorMsg.text =
                    context.resources.getString(stringId)
                false
            }

            else -> {
                errorMsg.visibility = View.INVISIBLE
                true
            }
        }
    }

    fun validPass(inputpass: String, errorMsg: TextView, context: Context, stringId: Int): Boolean {
        return when {
            inputpass.isEmpty() -> {
                errorMsg.visibility = View.VISIBLE
                errorMsg.text =
                    context.resources.getString(stringId)
                false
            }
            else -> {
                errorMsg.visibility = View.INVISIBLE
                true
            }
        }
    }
}