package com.victorthanh.utilslib.utils.helper

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


// Created by Nguyen Trung Thanh on 9/10/2020.
// Copyright (c) 2020 FUVI jsc. All rights reserved.
object FontManager {
    fun getTypeface(context: Context): Typeface {
        val uri = Uri.parse("android.resource://" + context.packageName + "/font/fuvi_icon.ttf")
        return Typeface.createFromAsset(context.assets, uri.path)
    }

    fun markAsIconContainer(v: View, typeface: Typeface?) {
        if (v is ViewGroup) {
            val vg = v
            for (i in 0 until vg.childCount) {
                val child: View = vg.getChildAt(i)
                markAsIconContainer(child, typeface)
            }
        } else if (v is TextView) {
            v.typeface = typeface
        }
    }

    fun changeColorText(v: View, color: Int) {
        if (v is ViewGroup) {
            val vg = v
            for (i in 0 until vg.childCount) {
                val child: View = vg.getChildAt(i)
                changeColorText(child, color)
            }
        } else if (v is TextView) {
            v.setTextColor(v.context.getColor(color))
        }
    }

    fun changeColorText(v: View, color: Int, id2find: IntArray) {
        if (v is ViewGroup) {
            val vg = v
            for (i in 0 until vg.childCount) {
                val child: View = vg.getChildAt(i)
                changeColorText(child, color, id2find)
            }
        } else if (id2find.contains(v.id)) {
            (v as TextView).setTextColor(v.context.getColor(color))
        }
    }
}