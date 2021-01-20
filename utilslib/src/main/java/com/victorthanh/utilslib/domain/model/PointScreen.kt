package com.victorthanh.utilslib.domain.model

// Created by Nguyen Trung Thanh on 9/11/2020.
// Copyright (c) 2020 FUVI jsc. All rights reserved.
class PointScreen(val scrwidth:Int,val scrheight:Int,val scrdensity:Float) {

    fun getDpWidth(): Int {
        return (scrwidth / scrdensity).toInt()
    }

    fun getDpHeight(): Int {
        return (scrheight / scrdensity).toInt()
    }

    fun dp2pixel(dpcount: Int): Int {
        return (dpcount * scrdensity + 0.5f).toInt()
    }
}