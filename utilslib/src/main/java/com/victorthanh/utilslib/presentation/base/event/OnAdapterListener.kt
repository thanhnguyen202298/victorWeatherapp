package com.victorthanh.utilslib.presentation.base.event

import android.view.View

interface OnAdapterListener<in T> {

    fun onSelectedItemListener(model: T, index: Int, view: View? = null) = Unit

    fun onSelectedItemLongClickListener(model: T, index: Int, view: View? = null) = Unit

    fun onBottomReachedListener(model: T, index: Int) = Unit

    fun onSave(model: T, view: View?) = Unit
    fun onShowAttachment(model: T, view: View?) = Unit
    fun onShowEditLayout(model: T, view: View?, position: Int) = Unit

}