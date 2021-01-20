package com.victorthanh.utilslib.presentation.base

import android.os.Bundle
import android.view.View
import com.victorthanh.utilslib.domain.model.message.AppMessage
import com.google.android.material.textfield.TextInputLayout

interface BaseActivityImp {
    fun setupView()
    fun showProgressLoading(message: String = "")

    fun hideProgressLoading()

    fun showMessageToast(message: String? = "")

    fun showSnackBarError(appMessage: AppMessage, holderSnackbar: View? = null)

    fun showSnackBar(messageInfo: String, holderSnackbar: View? = null)

    fun showErrorTextInputLayout(view: TextInputLayout, errorMessage: String)

    fun hideErrorTextInputLayout(view: TextInputLayout)

    fun visibleView(vararg view: View?)

    fun goneView(vararg view: View?)

    fun hideView(vararg view: View?)

    fun onEvent(message: AppMessage, bundle: Bundle)

    fun navigateToHomeActivity()
}